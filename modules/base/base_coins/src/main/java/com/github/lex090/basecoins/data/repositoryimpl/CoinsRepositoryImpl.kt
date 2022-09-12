package com.github.lex090.basecoins.data.repositoryimpl

import android.accounts.NetworkErrorException
import com.github.lex090.basecoins.data.responses.CoinResponse
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
import com.github.lex090.coredbapi.entity.FavoriteCoinEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class CoinsRepositoryImpl @Inject constructor(
    private val service: CoinsNetworkService,
    private val favoriteCoinsDao: FavoriteCoinsDao,
    private val dispatcherIo: CoroutineContext
) : ICoinsRepository {

    override suspend fun getCoinsList(): ResultOf<List<Coin>> = withContext(dispatcherIo) {
        try {
            val favoriteCoins = async { favoriteCoinsDao.getFavoriteCoins() }
            val coinsMarketList = async { service.getCoinsMarketsList() }
            val coinsList =
                mapData(
                    marketCoinsResponse = coinsMarketList.await(),
                    favoritesCoins = favoriteCoins.await()
                )
            ResultOf.Success(coinsList)
        } catch (e: NetworkErrorException) {
            ResultOf.Error(exception = e)
        }
    }

    private fun mapData(
        marketCoinsResponse: List<CoinResponse>,
        favoritesCoins: List<FavoriteCoinEntity>
    ): List<Coin> {
        val listOfPairs = favoritesCoins.map { it.coinId to it }.toTypedArray()
        val favoritesMap = hashMapOf(*listOfPairs)

        return marketCoinsResponse.map { coinResponse ->
            Coin(
                id = coinResponse.id,
                name = coinResponse.name,
                price = coinResponse.currentPrice,
                isFavorite = favoritesMap[coinResponse.id] != null
            )
        }
    }
}