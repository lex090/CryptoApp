package com.github.lex090.basefavoriteimpl.data

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coredbapi.entity.FavoriteCoinEntity

fun Coin.toCoin(): FavoriteCoinEntity = FavoriteCoinEntity(coinId = id)