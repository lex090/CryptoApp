package com.github.lex090.basecoinsimpl.di

import com.github.lex090.basecoinsapi.domain.entity.CoinsList
import com.github.lex090.basecoinsimpl.data.mappers.CoinMapperImpl
import com.github.lex090.basecoinsimpl.data.repositoryimpl.CoinsRepositoryImpl
import com.github.lex090.basecoinsimpl.data.responses.CoinResponse
import com.github.lex090.basecoinsimpl.data.services.CoinsNetworkService
import com.github.lex090.basecoinsimpl.domain.ICoinsRepository
import com.github.lex090.basecoinsimpl.domain.usecases.GetCoinsListUseCase
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.data.IMapper
import com.github.lex090.coreapi.domain.IBaseUseCase
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
    fun bind_GetCoinsListUseCase_to_IBaseUseCase(
        useCase: GetCoinsListUseCase
    ): IBaseUseCase<Any, ResultOf<@JvmSuppressWildcards CoinsList>>

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