package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.Coin


data class CoinUiEntity(
    val coinName: String
)

fun Coin.toCoinUiEntity(): CoinUiEntity = CoinUiEntity(coinName = this.coinName)