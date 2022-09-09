package com.github.lex090.featurecoinslistfragmentimpl.data.services

import com.github.lex090.featurecoinslistfragmentimpl.data.CoinsListNetworkMap.coinsListEntryPoint
import com.github.lex090.featurecoinslistfragmentimpl.data.responses.CoinsListResponse
import retrofit2.http.GET


interface CoinsNetworkService {

    @GET(coinsListEntryPoint)
    suspend fun getCoinsList(): CoinsListResponse
}