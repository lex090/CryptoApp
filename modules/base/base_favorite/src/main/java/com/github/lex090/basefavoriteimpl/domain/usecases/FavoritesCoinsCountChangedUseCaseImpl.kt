package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coreapi.domain.IBaseEmptyInputFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface IFavoritesCoinsCountChangedUseCase : IBaseEmptyInputFlowUseCase<Int>

internal class FavoritesCoinsCountChangedUseCaseImpl @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) : IFavoritesCoinsCountChangedUseCase {

    override fun execute(): Flow<Int> =
        favoritesRepository
            .subscribeOnFavoriteCoinsUpdating()
            .map { items ->
                items.size
            }
}