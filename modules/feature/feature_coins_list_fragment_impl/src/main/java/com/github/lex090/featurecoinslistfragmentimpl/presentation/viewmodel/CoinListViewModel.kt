package com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.lex090.basecoins.data.CoinsPagingSource
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.di.GetCoinsListUseCaseDependence
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basecoins.domain.usecases.IGetCoinsListUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinListViewModel(
    private val getCoinsListUseCase: IGetCoinsListUseCase,
    private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
    private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
    private val networkService: CoinsNetworkService
) : ViewModel() {

    val coinsList: StateFlow<PagingData<Coin>> = Pager(
        PagingConfig(
            pageSize = 250
        )
    ) {
        CoinsPagingSource(networkService)
    }.flow
        .onEach {
            Log.i("myDebug", "flow: flow")
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun clickOnAddCoinToFavorites(position: Int, coin: Coin) {
        viewModelScope.launch {
            addCoinToFavoritesUseCase.execute(data = coin)
            val newCoin = coin.copy(isFavorite = true)
//            changeListItem(position, newCoin)
        }
    }

    fun clickOnRemoveCoinFromFavorites(position: Int, coin: Coin) {
        viewModelScope.launch {
            removeCoinFromFavoritesUseCase.execute(data = coin)
            val newCoin = coin.copy(isFavorite = false)
//            changeListItem(position, newCoin)
        }
    }


    class Factory @Inject constructor(
        @GetCoinsListUseCaseDependence
        private val getCoinsListUseCase: IGetCoinsListUseCase,
        private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
        private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
        private val coinsNetworkService: CoinsNetworkService
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CoinListViewModel::class.java)
            return CoinListViewModel(
                getCoinsListUseCase,
                addCoinToFavoritesUseCase,
                removeCoinFromFavoritesUseCase,
                coinsNetworkService
            ) as T
        }
    }
}