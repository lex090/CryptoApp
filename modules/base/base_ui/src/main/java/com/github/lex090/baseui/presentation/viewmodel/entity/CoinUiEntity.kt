package com.github.lex090.baseui.presentation.viewmodel.entity

import com.github.lex090.basecoins.domain.entity.Coin

interface DisplayableItem

data class CoinUiEntity(
    val rang: String,
    val symbol: String,
    val name: String,
    val price: String,
    val priceChanging: String,
    val isFavorite: Boolean,
    val imageUrl: String?,
    val originalData: Coin
) : DisplayableItem

fun Coin.toCoinUiEntity(): CoinUiEntity =
    CoinUiEntity(
        rang = rang.toFormattedRangText(),
        symbol = symbol.toFormattedSymbolText(),
        name = this.name,
        price = price.toFormattedPriceText(),
        priceChanging = priceChanging.toFormattedPriceChangingText(),
        isFavorite = isFavorite,
        imageUrl = imageUrl,
        originalData = this
    )

fun CoinUiEntity.toCoin(
    isFavoriteNewValue: Boolean? = null
): Coin = originalData.copy(isFavorite = isFavoriteNewValue ?: isFavorite)

private const val MARKET_DATA_DIVIDER = 1000000000
const val DEFAULT_VALUE = "NaN"

fun String?.toFormattedRangText() =
    if (this == null)
        DEFAULT_VALUE
    else
        "#$this"

fun String?.toFormattedSymbolText() = this?.uppercase() ?: DEFAULT_VALUE

fun Double?.toFormattedMarketDataText() =
    if (this == null)
        DEFAULT_VALUE
    else {
        val value = this / MARKET_DATA_DIVIDER
        val formattedValue = "%.2f".format(value)
        "$$formattedValue B"
    }

fun Double?.toFormattedPriceText() = "${this}$" // "%.2f$".format(this)

fun Double?.toFormattedPriceChangingText() =
    if (this == null)
        DEFAULT_VALUE
    else
        "${"%.2f".format(this)}%"
