package com.github.lex090.basefavoriteimpl.data.repositoryimpl

import com.github.lex090.basecoins.data.mapData
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
import javax.inject.Inject

internal class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteCoinDao: FavoriteCoinsDao,
    private val coinsNetworkService: CoinsNetworkService
) : IFavoritesRepository {
    override suspend fun getFavoriteCoins(): List<Coin> {
        val favoriteCoins = favoriteCoinDao.getFavoriteCoins()
        return coinsNetworkService
            .getCoinsMarketsList(ids = favoriteCoins.joinToString(","))
            .mapData { true }
    }
}