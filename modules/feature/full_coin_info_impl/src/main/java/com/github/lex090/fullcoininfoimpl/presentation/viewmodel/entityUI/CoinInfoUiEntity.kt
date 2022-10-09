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

fun Coin.toCoinInfoUiEntity(): CoinInfoUiEntity =
    CoinInfoUiEntity(
        rang = if (rang == null) DEFAULT_VALUE else "#$rang",
        name = this.name,
        price = "$price$",
        isFavorite = isFavorite,
        imageUrl = imageUrl,
        symbol = if (symbol == null) DEFAULT_VALUE else symbol.toString().uppercase(),
        priceChanging = DEFAULT_VALUE,
        marketCap = if (marketCap == null) DEFAULT_VALUE else "$$marketCap B",
        volume24H = if (volume24h == null) DEFAULT_VALUE else "$$volume24h B",
        fullyDillMCap = if (fullyDilutedValuation == null) DEFAULT_VALUE else "$$fullyDilutedValuation B",
        description = description ?: DEFAULT_VALUE,
        links = listOf()
    )

data class IncreasePriceUiEntity(
    val price: Double
) : UiStateEntity

data class DecreasePriceUiEntity(
    val price: Double
) : UiStateEntity

data class FavoriteUiEntity(
    val isFavorite: Boolean
) : UiStateEntity