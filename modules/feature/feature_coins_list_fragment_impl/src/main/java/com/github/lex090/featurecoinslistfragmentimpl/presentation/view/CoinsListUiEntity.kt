package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.CoinsList

data class CoinsListUiEntity(
    val coinsList: List<CoinUiEntity>
)

fun CoinsList.toCoinsListUiEntity(): CoinsListUiEntity = CoinsListUiEntity(
    coinsList = this.coinsList.map { it.toCoinUiEntity() }
)
