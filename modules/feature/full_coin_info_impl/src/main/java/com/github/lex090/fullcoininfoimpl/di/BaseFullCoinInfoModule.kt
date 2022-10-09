package com.github.lex090.fullcoininfoimpl.di

import com.github.lex090.basecoins.di.BaseCoinsModule
import com.github.lex090.basefavoriteimpl.di.BaseFavoriteModule
import com.github.lex090.fullcoininfoimpl.data.FullCoinInfoRepositoryImpl
import com.github.lex090.fullcoininfoimpl.domain.GetCoinInfoUseCaseImpl
import com.github.lex090.fullcoininfoimpl.domain.GetLiveTimePriceOfCoinFlowUseCaseImpl
import com.github.lex090.fullcoininfoimpl.domain.IFullCoinInfoRepository
import com.github.lex090.fullcoininfoimpl.domain.IGetCoinInfoUseCase
import com.github.lex090.fullcoininfoimpl.domain.IGetLiveTimePriceOfCoinFlowUseCase
import com.github.lex090.fullcoininfoimpl.data.ScarletLifecycle
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.lifecycle.LifecycleRegistry
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        FullCoinInfoBindsModule::class,
        BaseCoinsModule::class,
        BaseFavoriteModule::class,
        FullCoinInfoModule::class
    ]
)
object BaseFullCoinInfoModule

@Module
class FullCoinInfoModule {

    @Provides
    fun provideLifeCycleRegistry(): LifecycleRegistry = LifecycleRegistry()

    @Singleton
    @Provides
    fun provideRealTimeServiceLifeCycle(
        lifecycleRegistry: LifecycleRegistry
    ): ScarletLifecycle =
        ScarletLifecycle(lifecycleRegistry)

    @Singleton
    @Provides
    fun provideRealTimeServiceLifeCycle2(
        lifecycleRegistry: ScarletLifecycle
    ): Lifecycle = lifecycleRegistry
}

@Suppress("FunctionName")
@Module
interface FullCoinInfoBindsModule {

    @Binds
    fun binds_GetCoinInfoUseCaseImpl_to_IGetCoinInfoUseCase(
        getCoinInfoUseCaseImpl: GetCoinInfoUseCaseImpl
    ): IGetCoinInfoUseCase

    @Binds
    fun binds_GetLiveTimePriceOfCoinFlowUseCaseImpl_to_IGetLiveTimePriceOfCoinFlowUseCase(
        getLiveTimePriceOfCoinFlowUseCaseImpl: GetLiveTimePriceOfCoinFlowUseCaseImpl
    ): IGetLiveTimePriceOfCoinFlowUseCase

    @Binds
    fun binds_FullCoinInfoRepositoryImpl_to_IFullCoinInfoRepository(
        fullCoinInfoRepositoryImpl: FullCoinInfoRepositoryImpl
    ): IFullCoinInfoRepository
}