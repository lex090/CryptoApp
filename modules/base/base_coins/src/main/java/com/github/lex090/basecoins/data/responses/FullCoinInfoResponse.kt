package com.github.lex090.basecoins.data.responses

import com.squareup.moshi.Json

data class FullCoinInfoResponse(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "symbol")
    val symbol: String = "",
    @Json(name = "description")
    val description: Description,
    @Json(name = "market_cap_rank")
    val marketCapRank: Int = 0,
    @Json(name = "links")
    val links: Links,
    @Json(name = "categories")
    val categories: List<String>?,
    @Json(name = "image")
    val image: Image,
    @Json(name = "last_updated")
    val lastUpdated: String = "",
    @Json(name = "community_data")
    val communityData: CommunityData,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "coingecko_rank")
    val coinGeckoRank: Int = 0,
)