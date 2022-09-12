package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.data.toCoin
import com.github.lex090.coreapi.domain.IBaseUseCase
import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface IAddCoinToFavoritesUseCase : IBaseUseCase<Coin, Unit>

internal class AddCoinToFavoritesUseCaseImpl @Inject constructor(
    private val favoriteCoinDao: FavoriteCoinsDao,
    private val dispatcherIo: CoroutineContext
) : IAddCoinToFavoritesUseCase {

    override suspend fun execute(data: Coin?) {
        requireNotNull(data)

        withContext(dispatcherIo) {
            favoriteCoinDao.addCoinToFavorites(data.toCoin())
        }
    }
}