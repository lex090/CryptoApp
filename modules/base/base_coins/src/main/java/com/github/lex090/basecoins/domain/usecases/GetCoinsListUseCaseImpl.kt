package com.github.lex090.basecoins.domain.usecases

import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.domain.IBaseEmptyInputUseCase
import javax.inject.Inject

interface IGetCoinsListUseCase : IBaseEmptyInputUseCase<ResultOf<List<Coin>>>

internal class GetCoinsListUseCaseImpl @Inject constructor(
    private val repository: ICoinsRepository
) : IGetCoinsListUseCase {

    override suspend fun execute(): ResultOf<List<Coin>> =
        repository.getCoinsList()
}