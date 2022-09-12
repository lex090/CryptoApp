package com.github.lex090.basefavoriteimpl.di

import com.github.lex090.basefavoriteimpl.domain.usecases.AddCoinToFavoritesUseCaseImpl
import com.github.lex090.basefavoriteimpl.domain.usecases.FavoritesCoinsCountChangedUseCaseImpl
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IFavoritesCoinsCountChangedUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.RemoveCoinFromFavoritesUseCaseImpl
import dagger.Binds
import dagger.Module


@Module(
    includes = [
        FavoriteBindsModule::class
    ]
)
object BaseFavoriteModule

@Suppress("FunctionName")
@Module
internal interface FavoriteBindsModule {

    @Binds
    fun bind_AddCoinToFavoritesUseCaseImpl_to_IAddCoinToFavoritesUseCase(
        useCase: AddCoinToFavoritesUseCaseImpl
    ): IAddCoinToFavoritesUseCase

    @Binds
    fun bind_RemoveCoinFromFavoritesUseCaseImpl_to_IRemoveCoinFromFavoritesUseCase(
        useCase: RemoveCoinFromFavoritesUseCaseImpl
    ): IRemoveCoinFromFavoritesUseCase

    @Binds
    fun bind_FavoritesCoinsCountChangedUseCaseImpl_to_IFavoritesCoinsCountChangedUseCase(
        useCase: FavoritesCoinsCountChangedUseCaseImpl
    ): IFavoritesCoinsCountChangedUseCase
}