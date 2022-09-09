package com.github.lex090.featurecoinslistfragmentimpl.data.repositoryimpl

import android.accounts.NetworkErrorException
import com.github.lex090.coreapi.data.IMapper
import com.github.lex090.corenetworkapi.ResultOf
import com.github.lex090.featurecoinslistfragmentimpl.data.responses.CoinResponse
import com.github.lex090.featurecoinslistfragmentimpl.data.services.CoinsNetworkService
import com.github.lex090.featurecoinslistfragmentimpl.domain.ICoinsRepository
import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.CoinsList
import javax.inject.Inject

class CoinsRepositoryImpl @Inject constructor(
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