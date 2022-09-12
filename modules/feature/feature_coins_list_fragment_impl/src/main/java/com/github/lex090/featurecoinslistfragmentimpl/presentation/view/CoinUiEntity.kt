package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

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
        isFavorite = false
    )

fun CoinUiEntity.toCoin(): Coin = Coin(id = id, name = name, price = price)