package com.github.lex090.basefavoriteimpl.data.repositoryimpl

import com.github.lex090.basecoins.data.mapData
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.data.toCoin
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
        return@withContext if (favoriteCoins.isEmpty()) {
            listOf()
        } else {
            coinsNetworkService
                .getCoinsMarketsList(
                    ids = favoriteCoins.joinToString(",") { it.coinId }
                )
                .mapData { true }
        }
    }

    override suspend fun updateFavoriteCoin(coin: Coin) =
        favoriteCoinDao.updateFavoriteCoin(coin.toFavoriteCoinEntity())

    override suspend fun updateFavoriteCoins(coins: List<Coin>) =
        favoriteCoinDao.updateFavoriteCoin(coins.map { it.toFavoriteCoinEntity() })

    override suspend fun addCoinToFavorites(coin: Coin) =
        favoriteCoinDao.addCoinToFavorites(coin.toFavoriteCoinEntity())

    override suspend fun addCoinsToFavorites(coins: List<Coin>) =
        favoriteCoinDao.addCoinsToFavorites(coins.map { it.toFavoriteCoinEntity() })

    override suspend fun removeCoinFromFavorites(coin: Coin) =
        favoriteCoinDao.removeCoinFromFavorites(coin.toFavoriteCoinEntity().coinId)

    override suspend fun clearFavorites() = favoriteCoinDao.clearFavorites()

    override fun subscribeOnFavoriteCoinsUpdating(): Flow<List<Coin>> =
        favoriteCoinDao
            .subscribeOnFavoriteCoinsDbUpdating()
            .distinctUntilChanged()
            .map { items ->
                items.map { item ->
                    item.toCoin()
                }
            }
            .flowOn(dispatcherIo)
}