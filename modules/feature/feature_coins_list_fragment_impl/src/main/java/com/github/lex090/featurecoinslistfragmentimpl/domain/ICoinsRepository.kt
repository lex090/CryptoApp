package com.github.lex090.featurecoinslistfragmentimpl.domain

import com.github.lex090.corenetworkapi.ResultOf
import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.CoinsList

interface ICoinsRepository {

    suspend fun getCoinsList(): ResultOf<CoinsList>
}