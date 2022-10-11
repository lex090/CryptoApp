package com.github.lex090.basecoins.domain

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coreapi.ResultOf
import kotlinx.coroutines.flow.Flow

internal interface ICoinsRepository {

    fun getCoinsListFlow(): Flow<ResultOf<List<Coin>>>
}