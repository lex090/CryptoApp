package com.github.lex090.fullcoininfoimpl.domain

import com.github.lex090.coreapi.domain.IBaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IGetLiveTimePriceOfCoinFlowUseCase :
    IBaseFlowUseCase<String, Double>

class GetLiveTimePriceOfCoinFlowUseCaseImpl @Inject constructor(
    private val fullCoinInfoRepository: IFullCoinInfoRepository
) : IGetLiveTimePriceOfCoinFlowUseCase {

    override fun execute(data: String): Flow<Double> =
        fullCoinInfoRepository.getRealTimePriceOfCoin(coinId = data)

}