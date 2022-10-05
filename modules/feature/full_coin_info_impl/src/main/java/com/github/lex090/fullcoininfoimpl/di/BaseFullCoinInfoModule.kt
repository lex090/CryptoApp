package com.github.lex090.fullcoininfoimpl.di

import com.github.lex090.fullcoininfoimpl.domain.GetCoinInfoUseCaseImpl
import com.github.lex090.fullcoininfoimpl.domain.GetLiveTimePriceOfCoinFlowUseCaseImpl
import com.github.lex090.fullcoininfoimpl.domain.IGetCoinInfoUseCase
import com.github.lex090.fullcoininfoimpl.domain.IGetLiveTimePriceOfCoinFlowUseCase
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        FullCoinInfoBindsModule::class
    ]
)
object BaseFullCoinInfoModule

@Suppress("FunctionName")
@Module
interface FullCoinInfoBindsModule {

    @Binds
    fun binds_GetCoinInfoUseCaseImpl_to_IGetCoinInfoUseCase(
        getCoinInfoUseCaseImpl: GetCoinInfoUseCaseImpl
    ): IGetCoinInfoUseCase

    @Binds
    fun binds_GetLiveTimePriceOfCoinFlowUseCaseImpl_to_IGetLiveTimePriceOfCoinFlowUseCase(
        GetLiveTimePriceOfCoinFlowUseCaseImpl: GetLiveTimePriceOfCoinFlowUseCaseImpl
    ): IGetLiveTimePriceOfCoinFlowUseCase
}