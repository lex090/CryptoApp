package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coreapi.domain.IBaseUseCase
import javax.inject.Inject

interface IAddCoinToFavoritesUseCase : IBaseUseCase<Coin, Unit>

internal class AddCoinToFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) : IAddCoinToFavoritesUseCase {

    override suspend fun execute(data: Coin) =
        favoritesRepository.addCoinToFavorites(coin = data)
}