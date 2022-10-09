package com.github.lex090.basecoins.data.responses

import com.squareup.moshi.Json

data class FullCoinInfoResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "market_cap_rank")
    val marketCapRank: Int? = 0,
    @Json(name = "image")
    val image: Image? = null,
    @Json(name = "symbol")
    val symbol: String? = null,
    @Json(name = "name")
    val name: String,
    @Json(name = "market_data")
    val marketData: MarketData,
    @Json(name = "description")
    val description: Description?,
//    @Json(name = "links")
//    val links: Links,
//    @Json(name = "community_data")
//    val communityData: CommunityData,
)

data class MarketData(
    @Json(name = "current_price")
    val price: CurrentPrice,
    @Json(name = "market_cap")
    val marketCap: MarketCap? = null,
    @Json(name = "total_volume")
    val totalVolume: TotalVolume? = null,
    @Json(name = "fully_diluted_valuation")
    val fullyDilutedValuation: FullyDilutedValuation? = null,
)

data class CurrentPrice(
    val usd: Double
)

data class MarketCap(
    val usd: Double?
)

data class FullyDilutedValuation(
    val usd: Double?
)

data class TotalVolume(
    val usd: Double?
)