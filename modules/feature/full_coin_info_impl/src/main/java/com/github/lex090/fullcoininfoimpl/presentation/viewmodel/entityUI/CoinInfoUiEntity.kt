package com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.baseui.presentation.viewmodel.entity.DEFAULT_VALUE
import com.github.lex090.baseui.presentation.viewmodel.entity.toFormattedMarketDataText
import com.github.lex090.baseui.presentation.viewmodel.entity.toFormattedPriceText
import com.github.lex090.baseui.presentation.viewmodel.entity.toFormattedRangText
import com.github.lex090.baseui.presentation.viewmodel.entity.toFormattedSymbolText
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
    val originalData: Coin
) : UiStateEntity

fun Coin.toCoinInfoUiEntity(): CoinInfoUiEntity =
    CoinInfoUiEntity(
        rang = rang.toFormattedRangText(),
        name = this.name,
        price = price.toFormattedPriceText(),
        isFavorite = isFavorite,
        imageUrl = imageUrl,
        symbol = symbol.toFormattedSymbolText(),
        priceChanging = DEFAULT_VALUE,
        marketCap = marketCap.toFormattedMarketDataText(),
        volume24H = volume24h.toFormattedMarketDataText(),
        fullyDillMCap = fullyDilutedValuation.toFormattedMarketDataText(),
        description = description ?: DEFAULT_VALUE,
        originalData = this
    )