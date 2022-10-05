package com.github.lex090.fullcoininfoimpl.presentation.view.entity

import com.github.lex090.basecoins.domain.entity.Coin

data class CoinInfoUiEntity(
    val id: String,
    val name: String,
    val price: Double,
    val isFavorite: Boolean
)


fun Coin.toCoinInfoUiEntity(): CoinInfoUiEntity =
    CoinInfoUiEntity(
        id = id,
        name = this.name,
        price = price,
        isFavorite = isFavorite
    )

fun CoinInfoUiEntity.toCoin(isFavoriteNewValue: Boolean? = null): Coin = Coin(
    id = id,
    name = name,
    price = price,
    isFavorite = isFavoriteNewValue ?: isFavorite
)