package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coreapi.domain.IBaseUseCase
import javax.inject.Inject

interface IRemoveCoinFromFavoritesUseCase : IBaseUseCase<Coin, Unit>

internal class RemoveCoinFromFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) : IRemoveCoinFromFavoritesUseCase {

    override suspend fun execute(data: Coin) =
        favoritesRepository.removeCoinFromFavorites(data)
}