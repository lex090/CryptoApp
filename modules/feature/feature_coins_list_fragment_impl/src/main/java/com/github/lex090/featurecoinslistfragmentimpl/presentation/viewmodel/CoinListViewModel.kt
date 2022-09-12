package com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basecoins.di.GetCoinsListUseCaseDependence
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basecoins.domain.entity.CoinsList
import com.github.lex090.basecoins.domain.usecases.IGetCoinsListUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import com.github.lex090.coreapi.ResultOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinListViewModel(
    private val getCoinsListUseCase: IGetCoinsListUseCase,
    private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
    private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
) : ViewModel() {

    private val _mutableCoinsListStateFlow: MutableStateFlow<List<Coin>> =
        MutableStateFlow(
            listOf()
        )
    val coinsList: StateFlow<List<Coin>> = _mutableCoinsListStateFlow


    fun onViewsInit() {
        viewModelScope.launch {
            when (val result = getCoinsFromRepository()) {
                is ResultOf.Error -> {

                }
                is ResultOf.Success -> {
                    _mutableCoinsListStateFlow.value = result.data.coinsList
                }
            }
        }
    }

    fun clickOnAddCoinToFavorites(position: Int, coin: Coin) {
        viewModelScope.launch {
            addCoinToFavoritesUseCase.execute(data = coin)
            val newCoin = coin.copy(isFavorite = true)
            changeListItem(position, newCoin)
        }
    }

    fun clickOnRemoveCoinFromFavorites(position: Int, coin: Coin) {
        viewModelScope.launch {
            removeCoinFromFavoritesUseCase.execute(data = coin)
            val newCoin = coin.copy(isFavorite = false)
            changeListItem(position, newCoin)
        }
    }

    private fun changeListItem(
        position: Int,
        coin: Coin
    ) {
        val coinList = coinsList.value
        val items = mutableListOf<Coin>()
        items.addAll(coinList)
        items[position] = coin
        _mutableCoinsListStateFlow.value = items
    }

    private suspend fun getCoinsFromRepository(): ResultOf<CoinsList> =
        getCoinsListUseCase.execute()


    class Factory @Inject constructor(
        @GetCoinsListUseCaseDependence
        private val getCoinsListUseCase: IGetCoinsListUseCase,
        private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
        private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CoinListViewModel::class.java)
            return CoinListViewModel(
                getCoinsListUseCase,
                addCoinToFavoritesUseCase,
                removeCoinFromFavoritesUseCase
            ) as T
        }
    }
}