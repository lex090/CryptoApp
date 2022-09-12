package com.github.lex090.basecoins.di

import com.github.lex090.basecoins.data.mappers.CoinMapperImpl
import com.github.lex090.basecoins.data.repositoryimpl.CoinsRepositoryImpl
import com.github.lex090.basecoins.data.responses.CoinResponse
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.basecoins.domain.entity.CoinsList
import com.github.lex090.basecoins.domain.usecases.GetCoinsListUseCaseImpl
import com.github.lex090.basecoins.domain.usecases.IGetCoinsListUseCase
import com.github.lex090.coreapi.data.IMapper
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
    fun bind_GetCoinsListUseCase_to_IBaseUseCase(
        useCase: GetCoinsListUseCaseImpl
    ): IGetCoinsListUseCase

    @Binds
    fun bind_CoinsRepositoryImpl_to_ICoinsRepository(
        repository: CoinsRepositoryImpl
    ): ICoinsRepository

    @Binds
    fun bind_CoinMapperImpl_to_IMapper(
        mapper: CoinMapperImpl
    ): IMapper<List<@JvmSuppressWildcards CoinResponse>, CoinsList>
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