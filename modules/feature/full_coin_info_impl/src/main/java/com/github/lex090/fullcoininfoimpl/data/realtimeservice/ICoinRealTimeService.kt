package com.github.lex090.fullcoininfoimpl.data.realtimeservice

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import kotlinx.coroutines.flow.Flow


interface ICoinRealTimeService {

    @Receive
    fun observeWebSocketEvent(): Flow<WebSocket.Event>
}