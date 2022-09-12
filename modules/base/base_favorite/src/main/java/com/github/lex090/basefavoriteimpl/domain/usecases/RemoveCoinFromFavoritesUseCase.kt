package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.data.toFavoriteCoinEntity
import com.github.lex090.coreapi.domain.IBaseUseCase
import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface IRemoveCoinFromFavoritesUseCase : IBaseUseCase<Coin, Unit>

internal class RemoveCoinFromFavoritesUseCaseImpl @Inject constructor(
    private val favoriteCoinDao: FavoriteCoinsDao,
    private val dispatcherIo: CoroutineContext
) : IRemoveCoinFromFavoritesUseCase {

    override suspend fun execute(data: Coin) =
        withContext(dispatcherIo) {
            favoriteCoinDao.removeCoinFromFavorites(data.toFavoriteCoinEntity().coinId)
        }
}