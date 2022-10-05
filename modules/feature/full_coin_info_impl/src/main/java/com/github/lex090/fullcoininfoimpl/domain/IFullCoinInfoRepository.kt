package com.github.lex090.fullcoininfoimpl.domain

import com.github.lex090.basecoins.domain.entity.Coin
import kotlinx.coroutines.flow.Flow

interface IFullCoinInfoRepository {

    suspend fun getFullCoinInfo(coinId: String): Coin

    fun getRealTimePriceOfCoin(coinId: String): Flow<Double>
}