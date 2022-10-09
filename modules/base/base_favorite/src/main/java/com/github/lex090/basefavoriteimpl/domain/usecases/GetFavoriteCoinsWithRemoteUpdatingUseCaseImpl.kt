package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.domain.IBaseEmptyInputFlowUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val REPEATING_TIME = 30_000L

interface IGetFavoriteCoinsWithRemoteUpdatingUseCase :
    IBaseEmptyInputFlowUseCase<ResultOf<List<Coin>>>

internal class GetFavoriteCoinsWithRemoteUpdatingUseCaseImpl @Inject constructor(
    private val favoritesRepository: IFavoritesRepository,
    private val dispatcherIo: CoroutineContext,
) : IGetFavoriteCoinsWithRemoteUpdatingUseCase {

    override fun execute(): Flow<ResultOf<List<Coin>>> = favoriteFlowFromDb()
        .combine(updatingFlow())
        { dbCoins, serverCoins ->
            val sortedList = filterAndSortedLists(serverCoins, dbCoins)
            ResultOf.Success(sortedList)
        }
        .flowOn(dispatcherIo)

    private fun filterAndSortedLists(
        serverCoins: List<Coin>,
        dbCoins: List<Coin>
    ): List<Coin> {
        val filteredCoins = serverCoins.filter { serverCoins ->
            dbCoins.any { it.id == serverCoins.id }
        }
        val dpKeyMap = dbCoins.mapIndexed { index, coin ->
            coin.id to index
        }.toMap()
        return filteredCoins.sortedBy { dpKeyMap[it.id] }
    }

    private fun updatingFlow(): Flow<List<Coin>> = flow {
        while (true) {
            val favorites = favoritesRepository.getFavoriteCoins()
            emit(favorites)
            favoritesRepository.updateFavoriteCoins(favorites)
            delay(REPEATING_TIME)
        }
    }

    private fun favoriteFlowFromDb() = favoritesRepository
        .subscribeOnFavoriteCoinsUpdating()
}