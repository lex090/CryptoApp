package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coreapi.domain.IBaseEmptyInputUseCase
import javax.inject.Inject


interface IClearFavoritesUseCaseUseCase : IBaseEmptyInputUseCase<Unit>

internal class ClearFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) : IClearFavoritesUseCaseUseCase {

    override suspend fun execute() = favoritesRepository.clearFavorites()
}