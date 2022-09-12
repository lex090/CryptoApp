package com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IGetFavoriteCoinsUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteCoinsViewModel(
    private val getFavoriteCoinsUseCase: IGetFavoriteCoinsUseCase,
    private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
    private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
) : ViewModel() {

    private val _mutableFavoriteCoinsListStateFlow: MutableStateFlow<List<Coin>> =
        MutableStateFlow(
            listOf()
        )
    val favoriteCoinsList: StateFlow<List<Coin>> = _mutableFavoriteCoinsListStateFlow

    fun onViewsInit() {
        viewModelScope.launch {
            val result = getFavoriteCoinsUseCase.execute()
            _mutableFavoriteCoinsListStateFlow.value = result
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
        val coinList = favoriteCoinsList.value
        val items = mutableListOf<Coin>()
        items.addAll(coinList)
        items[position] = coin
        _mutableFavoriteCoinsListStateFlow.value = items
    }


    class Factory @Inject constructor(
        private val getFavoriteCoinsUseCase: IGetFavoriteCoinsUseCase,
        private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
        private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == FavoriteCoinsViewModel::class.java)
            return FavoriteCoinsViewModel(
                getFavoriteCoinsUseCase = getFavoriteCoinsUseCase,
                addCoinToFavoritesUseCase = addCoinToFavoritesUseCase,
                removeCoinFromFavoritesUseCase = removeCoinFromFavoritesUseCase
            ) as T
        }
    }
}