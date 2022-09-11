package com.github.lex090.basecoinsimpl.domain.usecases

import com.github.lex090.basecoinsapi.domain.entity.CoinsList
import com.github.lex090.basecoinsimpl.domain.ICoinsRepository
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.domain.IBaseUseCase
import javax.inject.Inject

internal class GetCoinsListUseCase @Inject constructor(
    private val repository: ICoinsRepository
) : IBaseUseCase<Any, ResultOf<@JvmSuppressWildcards CoinsList>> {

    override suspend fun execute(data: Any?): ResultOf<CoinsList> =
        repository.getCoinsList()
}