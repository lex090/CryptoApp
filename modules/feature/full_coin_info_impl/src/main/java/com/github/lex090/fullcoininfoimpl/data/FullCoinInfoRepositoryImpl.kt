package com.github.lex090.fullcoininfoimpl.data

import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.usecases.IGetFavoriteCoinsUseCase
import com.github.lex090.fullcoininfoimpl.domain.IFullCoinInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FullCoinInfoRepositoryImpl @Inject constructor(
    private val service: CoinsNetworkService,
    private val favoritesRepository: IGetFavoriteCoinsUseCase,
    private val dispatcherIo: CoroutineContext
) : IFullCoinInfoRepository {

    override suspend fun getFullCoinInfo(coinId: String): Coin = withContext(dispatcherIo) {
        val coinInfoResponse = service.getCoinInfo(id = coinId)
        val isFavoriteCoin = favoritesRepository.execute().any { it.id == coinId }

//        coinInfoResponse.toCoin { isFavoriteCoin }
        Coin(
            id = coinId,
            coinInfoResponse.name,
            0.0,
            isFavoriteCoin
        )
    }

    override fun getRealTimePriceOfCoin(coinId: String): Flow<Double> {
        TODO("Not yet implemented")
    }
}