package com.github.lex090.fullcoininfoimpl.data

import android.util.Log
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.data.toCoin
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.usecases.IGetFavoriteCoinsUseCase
import com.github.lex090.corenetworkapi.RealTimeNetworkServiceFactory
import com.github.lex090.fullcoininfoimpl.data.realtimeservice.ICoinRealTimeService
import com.github.lex090.fullcoininfoimpl.domain.IFullCoinInfoRepository
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FullCoinInfoRepositoryImpl @Inject constructor(
    private val service: CoinsNetworkService,
    private val favoritesRepository: IGetFavoriteCoinsUseCase,
    private val dispatcherIo: CoroutineContext,
    private val realTimeNetworkServiceFactory: RealTimeNetworkServiceFactory.Factory
) : IFullCoinInfoRepository {

    override suspend fun getFullCoinInfo(coinId: String): Coin = withContext(dispatcherIo) {
        val coinInfoResponse = service.getCoinInfo(id = coinId)
        val isFavoriteCoin = favoritesRepository.execute().any { it.id == coinId }

        Log.i("myDebug", "getFullCoinInfo: coinInfoResponse -> $coinInfoResponse")
        return@withContext coinInfoResponse.toCoin(isFavoriteCoin)
    }

    override fun getRealTimePriceOfCoin(coinId: String): Flow<Double> {
        val service = realTimeNetworkServiceFactory
            .create(coinId)
            .create(ICoinRealTimeService::class.java)

        return service
            .observeWebSocketEvent()
            .map {
                if (it is WebSocket.Event.OnMessageReceived) {
                    (it.message as Message.Text).value.split(":")[1]
                        .replace("\"", "")
                        .replace("}", "")
                        .toDouble()
                } else {
                    0.0
                }
            }
    }
}