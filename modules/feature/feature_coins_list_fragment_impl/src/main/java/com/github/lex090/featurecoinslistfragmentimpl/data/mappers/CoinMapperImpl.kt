package com.github.lex090.featurecoinslistfragmentimpl.data.mappers

import com.github.lex090.coreapi.data.IMapper
import com.github.lex090.featurecoinslistfragmentimpl.data.responses.CoinResponse
import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.Coin
import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.CoinsList
import javax.inject.Inject

class CoinMapperImpl @Inject constructor() :
    IMapper<@JvmSuppressWildcards List<@JvmSuppressWildcards CoinResponse>, CoinsList> {

    override fun map(oldData: List<CoinResponse>): CoinsList =
        CoinsList(
            coinsList = oldData.map { Coin(coinName = it.name) }
        )
}