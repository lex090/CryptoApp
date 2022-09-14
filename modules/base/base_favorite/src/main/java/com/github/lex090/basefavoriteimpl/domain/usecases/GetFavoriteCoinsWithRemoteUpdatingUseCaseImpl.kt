package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coreapi.domain.IBaseEmptyInputFlowUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val REPEATING_TIME = 30_000L

interface IGetFavoriteCoinsWithRemoteUpdatingUseCase : IBaseEmptyInputFlowUseCase<List<Coin>>

internal class GetFavoriteCoinsWithRemoteUpdatingUseCaseImpl @Inject constructor(
    private val favoritesRepository: IFavoritesRepository,
    private val dispatcherIo: CoroutineContext,
    ) : IGetFavoriteCoinsWithRemoteUpdatingUseCase {

    override fun execute(): Flow<List<Coin>> = favoritesRepository
        .subscribeOnFavoriteCoinsUpdating()
        .combine(updatingFlow()) { coinsList, _ ->
            coinsList
        }
        .flowOn(dispatcherIo)

    private fun updatingFlow(): Flow<Boolean> = flow<Boolean> {
        while (true) {
            val favorites = favoritesRepository.getFavoriteCoins()
            favoritesRepository.updateFavoriteCoins(favorites)
            delay(REPEATING_TIME)
        }
    }.onStart {
        emit(true)
    }.flowOn(dispatcherIo)
}