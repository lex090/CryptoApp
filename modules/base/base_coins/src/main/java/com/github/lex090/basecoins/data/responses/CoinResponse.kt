package com.github.lex090.basecoins.data.responses

import com.squareup.moshi.Json

data class CoinResponse(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "market_cap_rank")
    val marketCapRank: Int = 0,
    @Json(name = "symbol")
    val symbol: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "price_change_percentage_24h")
    val priceChangePercentage24H: Double = 0.0,
    @Json(name = "image")
    val image: String = "",
    @Json(name = "current_price")
    val currentPrice: Double = 0.0,
    @Json(name = "market_cap")
    val marketCap: Double = 0.0
)