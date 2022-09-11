package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import com.github.lex090.basecoinsapi.domain.entity.CoinsList

data class CoinsListUiEntity(
    val coinsList: List<CoinUiEntity>
)

fun CoinsList.toCoinsListUiEntity(): CoinsListUiEntity =
    CoinsListUiEntity(
        coinsList = coinsList
            .mapIndexed { index, value ->
                value.toCoinUiEntity(index + 1)
            }
    )
