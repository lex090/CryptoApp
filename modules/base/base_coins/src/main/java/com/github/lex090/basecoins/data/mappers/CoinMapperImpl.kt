package com.github.lex090.basecoins.data.mappers

import com.github.lex090.basecoins.data.responses.CoinResponse
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basecoins.domain.entity.CoinsList
import com.github.lex090.coreapi.data.IMapper
import javax.inject.Inject

internal class CoinMapperImpl @Inject constructor() :
    IMapper<@JvmSuppressWildcards List<@JvmSuppressWildcards CoinResponse>, CoinsList> {

    override fun map(oldData: List<CoinResponse>): CoinsList =
        CoinsList(
            coinsList = oldData.map {
                Coin(
                    id = it.id,
                    name = it.name,
                    price = it.currentPrice,
                    isFavorite = false
                )
            }
        )
}