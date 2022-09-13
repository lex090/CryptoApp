package com.github.lex090.basefavoriteimpl.data

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coredbapi.data.entity.FavoriteCoinEntity

fun Coin.toFavoriteCoinEntity(): FavoriteCoinEntity =
    FavoriteCoinEntity(
        coinId = id,
        coinName = name,
        coinPrice = price,
        isFavoriteCoin = isFavorite
    )

fun FavoriteCoinEntity.toCoin(): Coin =
    Coin(
        id = coinId,
        name = coinName,
        price = coinPrice,
        isFavorite = isFavoriteCoin
    )