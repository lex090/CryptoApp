package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.data.toFavoriteCoinEntity
import com.github.lex090.coreapi.domain.IBaseUseCase
import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
import javax.inject.Inject

interface IAddCoinToFavoritesUseCase : IBaseUseCase<Coin, Unit>

internal class AddCoinToFavoritesUseCaseImpl @Inject constructor(
    private val favoriteCoinDao: FavoriteCoinsDao
) : IAddCoinToFavoritesUseCase {

    override suspend fun execute(data: Coin) =
        favoriteCoinDao.addCoinToFavorites(data.toFavoriteCoinEntity())
}