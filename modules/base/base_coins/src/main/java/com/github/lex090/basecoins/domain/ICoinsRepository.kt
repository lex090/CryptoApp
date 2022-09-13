package com.github.lex090.basecoins.domain

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coreapi.ResultOf

internal interface ICoinsRepository {

    suspend fun getCoinsList(): ResultOf<List<Coin>>
}