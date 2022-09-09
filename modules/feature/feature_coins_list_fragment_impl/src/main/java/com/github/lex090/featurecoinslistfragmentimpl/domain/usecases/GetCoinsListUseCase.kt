package com.github.lex090.featurecoinslistfragmentimpl.domain.usecases

import com.github.lex090.coreapi.domain.IBaseUseCase
import com.github.lex090.corenetworkapi.ResultOf
import com.github.lex090.featurecoinslistfragmentimpl.domain.ICoinsRepository
import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.CoinsList
import javax.inject.Inject

class GetCoinsListUseCase @Inject constructor(
    private val repository: ICoinsRepository
) : IBaseUseCase<ResultOf<@JvmSuppressWildcards CoinsList>> {

    override suspend fun execute(): ResultOf<CoinsList> = repository.getCoinsList()
}