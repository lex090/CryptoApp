package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.coreapi.domain.IBaseEmptyInputFlowUseCase
import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface IFavoritesCoinsCountChangedUseCase : IBaseEmptyInputFlowUseCase<Int>

internal class FavoritesCoinsCountChangedUseCaseImpl @Inject constructor(
    private val favoriteCoinDao: FavoriteCoinsDao,
    private val dispatcherIo: CoroutineContext
) : IFavoritesCoinsCountChangedUseCase {

    override fun execute(): Flow<Int> =
        favoriteCoinDao
            .subscribeOnFavoriteCoinsDbUpdating()
            .map { items ->
                items.size
            }
            .distinctUntilChanged()
            .flowOn(dispatcherIo)
}