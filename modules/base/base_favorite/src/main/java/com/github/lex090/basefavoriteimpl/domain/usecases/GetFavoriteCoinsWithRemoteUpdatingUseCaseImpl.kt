package com.github.lex090.basefavoriteimpl.domain.usecases

import android.util.Log
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coreapi.domain.IBaseEmptyInputFlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

interface IGetFavoriteCoinsWithRemoteUpdatingUseCase : IBaseEmptyInputFlowUseCase<List<Coin>>

internal class GetFavoriteCoinsWithRemoteUpdatingUseCaseImpl @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) : IGetFavoriteCoinsWithRemoteUpdatingUseCase {

    override fun execute(): Flow<List<Coin>> =
        favoritesRepository
            .subscribeOnFavoriteCoinsUpdating()
            .combine(updatingFlow()) { coinsList, _ -> coinsList }
            .onEach {
                Log.i("myDebug", "execute: onEach -> $it")
            }
            .onStart {
                favoritesRepository.getFavoriteCoins()
                Log.i("myDebug", "execute: onStart")
            }
            .flowOn(Dispatchers.IO)

    private fun updatingFlow(): Flow<Boolean> {
        return flow<Boolean> {
            while (true) {
                delay(5000)
                val favorites = favoritesRepository.getFavoriteCoins()
                Log.i("myDebug", "updatingFlow: favorites -> $favorites")
                favoritesRepository.addCoinsToFavorites(favorites)
            }
        }.onStart {
            emit(true)
        }
            .flowOn(Dispatchers.IO)
    }
}