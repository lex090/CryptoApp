package com.github.lex090.basecoinsimpl.domain

import com.github.lex090.basecoinsapi.domain.entity.CoinsList
import com.github.lex090.coreapi.ResultOf

internal interface ICoinsRepository {

    suspend fun getCoinsList(): ResultOf<CoinsList>
}