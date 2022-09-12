package com.github.lex090.basecoins.domain.usecases

import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.basecoins.domain.entity.CoinsList
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.domain.IBaseEmptyInputUseCase
import javax.inject.Inject

interface IGetCoinsListUseCase : IBaseEmptyInputUseCase<ResultOf<CoinsList>>

internal class GetCoinsListUseCaseImpl @Inject constructor(
    private val repository: ICoinsRepository
) : IGetCoinsListUseCase {

    override suspend fun execute(): ResultOf<CoinsList> =
        repository.getCoinsList()
}