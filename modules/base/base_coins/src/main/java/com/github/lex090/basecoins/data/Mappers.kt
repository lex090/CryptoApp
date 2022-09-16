package com.github.lex090.basecoins.data

import com.github.lex090.basecoins.data.responses.CoinResponse
import com.github.lex090.basecoins.domain.entity.Coin

fun List<CoinResponse>.mapData(
    isFavoritePredicate: (id: String) -> Boolean
): List<Coin> =
    map { coinResponse ->
        Coin(
            id = coinResponse.id ?: "",
            name = coinResponse.name ?: "",
            price = coinResponse.currentPrice ?: 0.0,
            isFavorite = isFavoritePredicate.invoke(coinResponse.id ?: "")
        )
    }