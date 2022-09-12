package com.github.lex090.basefavoriteimpl.data.repositoryimpl

import com.github.lex090.basecoins.data.mapData
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.data.toFavoriteCoinEntity
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coredbapi.data.dao.FavoriteCoinsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteCoinDao: FavoriteCoinsDao,
    private val coinsNetworkService: CoinsNetworkService,
    private val dispatcherIo: CoroutineContext,
) : IFavoritesRepository {

    override suspend fun getFavoriteCoins(): List<Coin> = withContext(dispatcherIo) {
        val favoriteCoins = favoriteCoinDao.getFavoriteCoins()
        return@withContext coinsNetworkService
            .getCoinsMarketsList(
                ids = favoriteCoins.joinToString(",")
            )
            .mapData { true }
    }

    override suspend fun addCoinToFavorites(coin: Coin) =
        favoriteCoinDao.addCoinToFavorites(coin.toFavoriteCoinEntity())

    override suspend fun removeCoinFromFavorites(coin: Coin) =
        favoriteCoinDao.removeCoinFromFavorites(coin.toFavoriteCoinEntity().coinId)

    override fun subscribeOnFavoriteCoinsUpdating(): Flow<Int> =
        favoriteCoinDao
            .subscribeOnFavoriteCoinsDbUpdating()
            .map { items ->
                items.size
            }
            .distinctUntilChanged()
            .flowOn(dispatcherIo)
}