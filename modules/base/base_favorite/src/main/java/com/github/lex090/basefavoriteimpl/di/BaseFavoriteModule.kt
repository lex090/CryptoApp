package com.github.lex090.basefavoriteimpl.di

import com.github.lex090.basecoins.di.BaseCoinsModule
import com.github.lex090.basefavoriteimpl.data.repositoryimpl.FavoritesRepositoryImpl
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.basefavoriteimpl.domain.usecases.AddCoinToFavoritesUseCaseImpl
import com.github.lex090.basefavoriteimpl.domain.usecases.ClearFavoritesUseCaseImpl
import com.github.lex090.basefavoriteimpl.domain.usecases.FavoritesCoinsCountChangedUseCaseImpl
import com.github.lex090.basefavoriteimpl.domain.usecases.GetFavoriteCoinsUseCaseImpl
import com.github.lex090.basefavoriteimpl.domain.usecases.GetFavoriteCoinsWithRemoteUpdatingUseCaseImpl
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IClearFavoritesUseCaseUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IFavoritesCoinsCountChangedUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IGetFavoriteCoinsUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IGetFavoriteCoinsWithRemoteUpdatingUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.RemoveCoinFromFavoritesUseCaseImpl
import dagger.Binds
import dagger.Module


@Module(
    includes = [
        FavoriteBindsModule::class,
        BaseCoinsModule::class
    ]
)
object BaseFavoriteModule

@Suppress("FunctionName")
@Module
internal interface FavoriteBindsModule {

    @Binds
    fun bind_FavoritesRepositoryImpl_to_IFavoritesRepository(
        repository: FavoritesRepositoryImpl
    ): IFavoritesRepository

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

    @Binds
    fun binds_GetFavoriteCoinsUseCaseImpl_to_IGetFavoriteCoinsUseCase(
        useCase: GetFavoriteCoinsUseCaseImpl
    ): IGetFavoriteCoinsUseCase

    @Binds
    fun binds_ClearFavoritesUseCaseImpl_to_IClearFavoritesUseCaseUseCase(
        useCase: ClearFavoritesUseCaseImpl
    ): IClearFavoritesUseCaseUseCase

    @Binds
    fun binds_GetFavoriteCoinsWithRemoteUpdatingUseCaseImpl_to_IGetFavoriteCoinsWithRemoteUpdatingUseCase(
        useCase: GetFavoriteCoinsWithRemoteUpdatingUseCaseImpl
    ): IGetFavoriteCoinsWithRemoteUpdatingUseCase
}