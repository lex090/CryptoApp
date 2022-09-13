package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coreapi.domain.IBaseEmptyInputFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

interface IGetFavoriteCoinsWithRemoteUpdatingUseCase : IBaseEmptyInputFlowUseCase<List<Coin>>

internal class GetFavoriteCoinsWithRemoteUpdatingUseCaseImpl @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) : IGetFavoriteCoinsWithRemoteUpdatingUseCase {

    override fun execute(): Flow<List<Coin>> = favoritesRepository
        .subscribeOnFavoriteCoinsUpdating()
        .onStart {
            favoritesRepository.getFavoriteCoins()
        }
}