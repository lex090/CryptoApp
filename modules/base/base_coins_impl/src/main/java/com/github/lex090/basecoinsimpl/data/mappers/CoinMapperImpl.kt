package com.github.lex090.basecoinsimpl.data.mappers

import com.github.lex090.basecoinsapi.domain.entity.Coin
import com.github.lex090.basecoinsapi.domain.entity.CoinsList
import com.github.lex090.basecoinsimpl.data.responses.CoinResponse
import com.github.lex090.coreapi.data.IMapper
import javax.inject.Inject

internal class CoinMapperImpl @Inject constructor() :
    IMapper<@JvmSuppressWildcards List<@JvmSuppressWildcards CoinResponse>, CoinsList> {

    override fun map(oldData: List<CoinResponse>): CoinsList =
        CoinsList(
            coinsList = oldData.map {
                Coin(
                    name = it.name,
                    price = it.currentPrice
                )
            }
        )
}