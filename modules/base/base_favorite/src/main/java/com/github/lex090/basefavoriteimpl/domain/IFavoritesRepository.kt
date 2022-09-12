package com.github.lex090.basefavoriteimpl.domain

import com.github.lex090.basecoins.domain.entity.Coin
import kotlinx.coroutines.flow.Flow

internal interface IFavoritesRepository {

    suspend fun getFavoriteCoins(): List<Coin>

    suspend fun addCoinToFavorites(coin: Coin)

    suspend fun removeCoinFromFavorites(coin: Coin)

    suspend fun clearFavorites()

    fun subscribeOnFavoriteCoinsUpdating(): Flow<Int>
}