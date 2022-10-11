package com.github.lex090.fullcoininfoimpl.domain

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coreapi.domain.IBaseUseCase
import javax.inject.Inject

interface IGetCoinInfoUseCase : IBaseUseCase<String, Coin>

class GetCoinInfoUseCaseImpl @Inject constructor(
    private val fullCoinInfoRepository: IFullCoinInfoRepository
) : IGetCoinInfoUseCase {

    override suspend fun execute(data: String): Coin =
        fullCoinInfoRepository.getFullCoinInfo(coinId = data)
}