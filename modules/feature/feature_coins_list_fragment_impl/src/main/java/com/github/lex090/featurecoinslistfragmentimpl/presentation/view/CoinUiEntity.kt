package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import com.github.lex090.basecoinsapi.domain.entity.Coin

interface DisplayableItem

data class CoinUiEntity(
    val position: Int,
    val name: String,
    val price: Double,
    val isFavorite: Boolean
) : DisplayableItem

fun Coin.toCoinUiEntity(position: Int): CoinUiEntity =
    CoinUiEntity(
        position = position,
        name = this.name,
        price = price,
        isFavorite = false
    )