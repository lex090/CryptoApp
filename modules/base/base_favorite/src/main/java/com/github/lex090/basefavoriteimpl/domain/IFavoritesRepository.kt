package com.github.lex090.basefavoriteimpl.domain

import com.github.lex090.basecoins.domain.entity.Coin

internal interface IFavoritesRepository {

    suspend fun getFavoriteCoins(): List<Coin>
}