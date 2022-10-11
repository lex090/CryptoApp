package com.github.lex090.basecoins.di

import com.github.lex090.basecoins.data.repositoryimpl.CoinsRepositoryImpl
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.basecoins.domain.usecases.GetCoinListFlowUseCaseImpl
import com.github.lex090.basecoins.domain.usecases.IGetCoinListFlowUseCase
import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        CoinsModule::class,
        CoinsBindsModule::class,
    ]
)
object BaseCoinsModule

@Suppress("FunctionName")
@Module
internal interface CoinsBindsModule {

    @Binds
    @GetCoinsListUseCaseDependence
    fun bind_GetCoinListFlowUseCaseImpl_to_IGetCoinListFlowUseCase(
        useCase: GetCoinListFlowUseCaseImpl
    ): IGetCoinListFlowUseCase

    @Binds
    fun bind_CoinsRepositoryImpl_to_ICoinsRepository(
        repository: CoinsRepositoryImpl
    ): ICoinsRepository
}

@Module
internal object CoinsModule {

    @Provides
    fun provideCoinsNetworkService(
        serviceGenerator: IRemoteNetworkServiceGenerator
    ): CoinsNetworkService = serviceGenerator.create(
        CoinsNetworkService::class.java
    )
}