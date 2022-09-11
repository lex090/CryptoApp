package com.github.lex090.basecoinsimpl.data.services

import com.github.lex090.basecoinsimpl.data.CoinsListNetworkMap.coinsMarketsEntryPoint
import com.github.lex090.basecoinsimpl.data.responses.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Query


internal interface CoinsNetworkService {

    @GET(coinsMarketsEntryPoint)
    suspend fun getCoinsMarketsList(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("ids") ids: String? = null,
        @Query("category") category: String? = null,
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
        @Query("price_change_percentage") priceChangePercentage: String = "1h,24h,7d,14d,30d",
    ): List<CoinResponse>
}