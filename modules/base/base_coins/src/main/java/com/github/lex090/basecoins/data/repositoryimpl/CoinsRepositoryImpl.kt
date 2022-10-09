package com.github.lex090.basecoins.data.repositoryimpl

import com.github.lex090.basecoins.data.mapData
import com.github.lex090.basecoins.data.responses.CoinResponse
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coredbapi.data.dao.FavoriteCoinsDao
import com.github.lex090.coredbapi.data.entity.FavoriteCoinEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CoinsRepositoryImpl @Inject constructor(
    private val service: CoinsNetworkService,
    private val favoriteCoinsDao: FavoriteCoinsDao,
) : ICoinsRepository {

    companion object {
        private const val POLLING_DELAY = 60_000L
    }

    override fun getCoinsListFlow(): Flow<ResultOf<List<Coin>>> =
        getPollingCoinsListFromServer()
            .combine(getFavoriteCoinsFlow()) { serverValue, dbValue ->
                val listOfPairs = dbValue.map { it.coinId to it }.toTypedArray()
                val favoritesMap = hashMapOf(*listOfPairs)

                serverValue.mapData { coinId ->
                    favoritesMap[coinId] != null
                }
            }
            .map {
                ResultOf.Success(it)
            }

    private fun getPollingCoinsListFromServer(): Flow<List<CoinResponse>> = flow {
        while (true) {
            emit(service.getCoinsMarketsList())
            delay(POLLING_DELAY)
        }
    }

    private fun getFavoriteCoinsFlow(): Flow<List<FavoriteCoinEntity>> =
        favoriteCoinsDao.subscribeOnFavoriteCoinsDbUpdating()
}