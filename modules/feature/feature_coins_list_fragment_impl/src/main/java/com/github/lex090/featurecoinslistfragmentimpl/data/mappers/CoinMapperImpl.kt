package com.github.lex090.featurecoinslistfragmentimpl.data.mappers

import com.github.lex090.coreapi.data.IMapper
import com.github.lex090.featurecoinslistfragmentimpl.data.responses.CoinsListResponse
import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.Coin
import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.CoinsList

class CoinMapperImpl : IMapper<CoinsListResponse, CoinsList> {

    override fun map(oldData: CoinsListResponse): CoinsList =
        CoinsList(
            coinsList = oldData.coinsList.map { Coin(coinName = it.coinName) }
        )
}