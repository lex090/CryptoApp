package com.github.lex090.basecoinsimpl.data.responses

import com.squareup.moshi.Json

internal data class CoinResponse(
    @Json(name = "price_change_percentage_24h")
    val priceChangePercentage24H: Double = 0.0,
    @Json(name = "symbol")
    val symbol: String = "",
    @Json(name = "total_volume")
    val totalVolume: Double? = 0.0,
    @Json(name = "price_change_24h")
    val priceChange24H: Double = 0.0,
    @Json(name = "atl_change_percentage")
    val atlChangePercentage: Double = 0.0,
    @Json(name = "market_cap_rank")
    val marketCapRank: Int = 0,
    @Json(name = "atl_date")
    val atlDate: String = "",
    @Json(name = "market_cap_change_24h")
    val marketCapChange24H: Double = 0.0,
    @Json(name = "market_cap")
    val marketCap: Long = 0,
    @Json(name = "ath")
    val ath: Double = 0.0,
    @Json(name = "high_24h")
    val high24H: Double = 0.0,
    @Json(name = "atl")
    val atl: Double = 0.0,
    @Json(name = "market_cap_change_percentage_24h")
    val marketCapChangePercentage24H: Double = 0.0,
    @Json(name = "id")
    val id: String = "",
    @Json(name = "price_change_percentage_7d_in_currency")
    val priceChangePercentage7DInCurrency: Double = 0.0,
    @Json(name = "ath_change_percentage")
    val athChangePercentage: Double = 0.0,
    @Json(name = "ath_date")
    val athDate: String = "",
    @Json(name = "image")
    val image: String = "",
    @Json(name = "last_updated")
    val lastUpdated: String = "",
    @Json(name = "total_supply")
    val totalSupply: Double? = 0.0,
    @Json(name = "price_change_percentage_24h_in_currency")
    val priceChangePercentage24HInCurrency: Double = 0.0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "low_24h")
    val lowH: Double = 0.0,
    @Json(name = "max_supply")
    val maxSupply: Double? = 0.0,
    @Json(name = "current_price")
    val currentPrice: Double = 0.0,
    @Json(name = "price_change_percentage_14d_in_currency")
    val priceChangePercentage14DInCurrency: Double = 0.0,
    @Json(name = "price_change_percentage_1h_in_currency")
    val priceChangePercentage1HInCurrency: Double? = 0.0,
    @Json(name = "price_change_percentage_30d_in_currency")
    val priceChangePercentage30DInCurrency: Double = 0.0
)