package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coreapi.domain.IBaseEmptyInputUseCase
import javax.inject.Inject


interface IGetFavoriteCoinsUseCase : IBaseEmptyInputUseCase<List<Coin>>

internal class GetFavoriteCoinsUseCaseImpl @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) : IGetFavoriteCoinsUseCase {

    override suspend fun execute(): List<Coin> =
        favoritesRepository.getFavoriteCoins()
}