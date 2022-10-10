package com.github.lex090.featurecoinslistfragmentimpl.presentation

import com.github.lex090.basecoins.domain.entity.Coin

val coin2 = Coin(
    id = "122",
    rang = "12",
    imageUrl = "https://test2.com",
    symbol = "BTC2",
    name = "bitcoin2",
    price = 20000.2,
    priceChanging = 0.04,
    isFavorite = true,
    marketCap = null,
    volume24h = null,
    fullyDilutedValuation = null,
    description = null
)

val coin1 = Coin(
    id = "12",
    rang = "1",
    imageUrl = "https://test.com",
    symbol = "BTC",
    name = "bitcoin",
    price = 20000.0,
    priceChanging = 0.02,
    isFavorite = true,
    marketCap = null,
    volume24h = null,
    fullyDilutedValuation = null,
    description = null
)

val testCoins = listOf(coin1, coin2)
