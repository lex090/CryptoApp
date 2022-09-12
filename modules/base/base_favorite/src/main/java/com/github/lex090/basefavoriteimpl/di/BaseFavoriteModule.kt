package com.github.lex090.basefavoriteimpl.di

import com.github.lex090.basefavoriteimpl.domain.usecases.AddCoinToFavoritesUseCaseImpl
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


@Module(
    includes = [
        FavoriteBindsModule::class,
        FavoriteModule::class
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
}

@Module
internal object FavoriteModule {

    @Provides
    fun provideDispatcherIo(): CoroutineContext = Dispatchers.IO
}