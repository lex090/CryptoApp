package com.github.lex090.featurecoinslistfragmentimpl.di

import com.github.lex090.coreapi.data.IMapper
import com.github.lex090.coreapi.domain.IBaseUseCase
import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator
import com.github.lex090.corenetworkapi.ResultOf
import com.github.lex090.featurecoinslistfragmentimpl.data.mappers.CoinMapperImpl
import com.github.lex090.featurecoinslistfragmentimpl.data.repositoryimpl.CoinsRepositoryImpl
import com.github.lex090.featurecoinslistfragmentimpl.data.responses.CoinResponse
import com.github.lex090.featurecoinslistfragmentimpl.data.services.CoinsNetworkService
import com.github.lex090.featurecoinslistfragmentimpl.domain.ICoinsRepository
import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.CoinsList
import com.github.lex090.featurecoinslistfragmentimpl.domain.usecases.GetCoinsListUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        CoinListFragmentBindsModule::class,
        CoinListFragmentModule::class
    ]
)
object BaseCoinListFragmentModule


@Suppress("FunctionName")
@Module
interface CoinListFragmentBindsModule {

    @Binds
    fun bind_GetCoinsListUseCase_to_IBaseUseCase(
        useCase: GetCoinsListUseCase
    ): IBaseUseCase<@JvmSuppressWildcards ResultOf<@JvmSuppressWildcards CoinsList>>

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
object CoinListFragmentModule {

    @Provides
    fun provideCoinsNetworkService(
        serviceGenerator: IRemoteNetworkServiceGenerator
    ): CoinsNetworkService = serviceGenerator.create(CoinsNetworkService::class.java)
}