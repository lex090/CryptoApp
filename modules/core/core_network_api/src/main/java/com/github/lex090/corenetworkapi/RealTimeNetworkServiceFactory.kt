package com.github.lex090.corenetworkapi

import android.util.Log
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.Stream
import com.tinder.scarlet.StreamAdapter
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.utils.getRawType
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import java.lang.reflect.Type

class RealTimeNetworkServiceFactory @AssistedInject constructor(
    @Assisted("coinId") private val coinId: String,
    private val okHttpClient: OkHttpClient,
    private val lifecycle: Lifecycle,
) {

    fun <ServiceType : Any> create(remoteService: Class<ServiceType>): ServiceType =
        createScarlet(coinId, lifecycle).create(remoteService)

    private fun createScarlet(
        coinId: String,
        lifecycle: Lifecycle
    ): Scarlet =
        Scarlet.Builder()
            .webSocketFactory(
                okHttpClient.newWebSocketFactory(
                    "wss://ws.coincap.io/prices?assets=$coinId"
                )
            )
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .build().apply {
                Log.i("myDebug", "createScarlet: $this")
            }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("coinId") coinId: String): RealTimeNetworkServiceFactory
    }
}

class FlowStreamAdapter<T> : StreamAdapter<T, Flow<T>> {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun adapt(stream: Stream<T>) = callbackFlow<T> {
        stream.start(object : Stream.Observer<T> {
            override fun onComplete() {
                close()
            }

            override fun onError(throwable: Throwable) {
                close(cause = throwable)
            }

            override fun onNext(data: T) {
                if (!isClosedForSend) offer(data)
            }
        })
        awaitClose {}
    }

    object Factory : StreamAdapter.Factory {
        override fun create(type: Type): StreamAdapter<Any, Any> {
            return when (type.getRawType()) {
                Flow::class.java -> FlowStreamAdapter()
                else -> throw IllegalArgumentException()
            }
        }
    }
}