package com.github.lex090.basecoins.data.repositoryimpl

import android.accounts.NetworkErrorException
import com.github.lex090.basecoins.data.mapData
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
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

            val listOfPairs = favoriteCoins.await().map { it.coinId to it }.toTypedArray()
            val favoritesMap = hashMapOf(*listOfPairs)

            val coinsList = coinsMarketList.await().mapData { coinId ->
                favoritesMap[coinId] != null
            }
            ResultOf.Success(coinsList)
        } catch (e: NetworkErrorException) {
            ResultOf.Error(exception = e)
        }
    }
}