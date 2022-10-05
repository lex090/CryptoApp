package com.github.lex090.fullcoininfoimpl.domain

import com.github.lex090.coreapi.domain.IBaseEmptyInputFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IGetLiveTimePriceOfCoinFlowUseCase :
    IBaseEmptyInputFlowUseCase<Double>

class GetLiveTimePriceOfCoinFlowUseCaseImpl @Inject constructor() :
    IGetLiveTimePriceOfCoinFlowUseCase {

    override fun execute(): Flow<Double> {
        TODO("Not yet implemented")
    }
}