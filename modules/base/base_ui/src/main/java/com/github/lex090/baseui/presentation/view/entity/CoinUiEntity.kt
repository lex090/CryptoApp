package com.github.lex090.baseui.presentation.view.entity

import com.github.lex090.basecoins.domain.entity.Coin

interface DisplayableItem

data class CoinUiEntity(
    val position: Int,
    val id: String,
    val name: String,
    val price: Double,
    val isFavorite: Boolean
) : DisplayableItem

fun Coin.toCoinUiEntity(position: Int): CoinUiEntity =
    CoinUiEntity(
        position = position,
        id = id,
        name = this.name,
        price = price,
        isFavorite = isFavorite
    )

fun CoinUiEntity.toCoin(isFavoriteNewValue: Boolean? = null): Coin = Coin(
    id = id,
    name = name,
    price = price,
    isFavorite = isFavoriteNewValue ?: isFavorite
)