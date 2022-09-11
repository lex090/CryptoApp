package com.github.lex090.basecoins.data.repositoryimpl

import android.accounts.NetworkErrorException
import com.github.lex090.basecoins.domain.entity.CoinsList
import com.github.lex090.basecoins.data.responses.CoinResponse
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.data.IMapper
import javax.inject.Inject

internal class CoinsRepositoryImpl @Inject constructor(
    private val service: CoinsNetworkService,
    private val mapper: IMapper<@JvmSuppressWildcards List<@JvmSuppressWildcards CoinResponse>, CoinsList>
) : ICoinsRepository {

    override suspend fun getCoinsList(): ResultOf<CoinsList> =
        try {
            val coinsList = mapper.map(service.getCoinsMarketsList())
            ResultOf.Success(coinsList)
        } catch (e: NetworkErrorException) {
            ResultOf.Error(exception = e)
        }
}