package com.github.lex090.basecoins.domain.usecases

import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.domain.IBaseEmptyInputFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IGetCoinListFlowUseCase : IBaseEmptyInputFlowUseCase<ResultOf<List<Coin>>>

internal class GetCoinListFlowUseCaseImpl @Inject constructor(
    private val repository: ICoinsRepository
) : IGetCoinListFlowUseCase {

    override fun execute(): Flow<ResultOf<List<Coin>>> =
        repository.getCoinsListFlow()
}
