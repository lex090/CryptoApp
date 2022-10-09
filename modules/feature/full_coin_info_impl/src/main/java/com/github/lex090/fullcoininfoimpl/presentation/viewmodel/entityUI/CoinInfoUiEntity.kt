package com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity

data class CoinInfoUiEntity(
    val rang: String,
    val imageUrl: String?,
    val symbol: String,
    val name: String,
    val price: String,
    val priceChanging: String,
    val isFavorite: Boolean,
    val marketCap: String,
    val volume24H: String,
    val fullyDillMCap: String,
    val description: String,
    val links: List<LinkUI>
) : UiStateEntity

data class LinkUI(
    val url: String,
    val host: String
)

private const val DEFAULT_VALUE = "NaN"
private const val MARKET_DATA_DIVIDER = 1000000000

fun Coin.toCoinInfoUiEntity(): CoinInfoUiEntity =
    CoinInfoUiEntity(
        rang = if (rang == null) DEFAULT_VALUE else "#$rang",
        name = this.name,
        price = "$price$",
        isFavorite = isFavorite,
        imageUrl = imageUrl,
        symbol = if (symbol == null) DEFAULT_VALUE else symbol.toString().uppercase(),
        priceChanging = DEFAULT_VALUE,
        marketCap = marketCap.toFormattedMarketDataText(),
        volume24H = volume24h.toFormattedMarketDataText(),
        fullyDillMCap = fullyDilutedValuation.toFormattedMarketDataText(),
        description = description ?: DEFAULT_VALUE,
        links = listOf()
    )

private fun Double?.toFormattedMarketDataText() =
    if (this == null)
        DEFAULT_VALUE
    else {
        val value = this / MARKET_DATA_DIVIDER
        val formattedValue = "%.2f".format(value)
        "$$formattedValue B"
    }

data class IncreasePriceUiEntity(
    val price: Double
) : UiStateEntity

data class DecreasePriceUiEntity(
    val price: Double
) : UiStateEntity

data class FavoriteUiEntity(
    val isFavorite: Boolean
) : UiStateEntity