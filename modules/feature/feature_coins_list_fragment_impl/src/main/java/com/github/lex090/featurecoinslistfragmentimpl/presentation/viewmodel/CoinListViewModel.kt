package com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basecoins.di.GetCoinsListUseCaseDependence
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basecoins.domain.usecases.IGetCoinsListUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import com.github.lex090.baseui.presentation.viewmodel.entity.toCoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.toCoinUiEntityList
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinListViewModel(
    private val getCoinsListUseCase: IGetCoinsListUseCase,
    private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
    private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
) : ViewModel() {

    private val _mutableScreenStateFlow: MutableStateFlow<BaseUiState<UiStateEntity>> =
        MutableStateFlow(BaseUiState.Loading)
    val screenState: MutableStateFlow<BaseUiState<UiStateEntity>> = _mutableScreenStateFlow

    fun initScreenStateSubscription() {
        viewModelScope.launch {
            _mutableScreenStateFlow.value = BaseUiState.Loading
            when (val result = getCoinsFromRepository()) {
                is ResultOf.Error -> {
                    _mutableScreenStateFlow.value =
                        BaseUiState.Error(result.exception, result.message)
                }
                is ResultOf.Success -> {
                    val data = result.data.map { it.toCoinUiEntity() }.toCoinUiEntityList()
                    _mutableScreenStateFlow.value = BaseUiState.Success(data)
                }
            }
        }
    }

    fun clickOnAddCoinToFavorites(coin: Coin) {
        viewModelScope.launch {
            val newCoin = coin.copy(isFavorite = true)
            addCoinToFavoritesUseCase.execute(data = newCoin)
        }
    }

    fun clickOnRemoveCoinFromFavorites(coin: Coin) {
        viewModelScope.launch {
            val newCoin = coin.copy(isFavorite = false)
            removeCoinFromFavoritesUseCase.execute(data = newCoin)
        }
    }

    private suspend fun getCoinsFromRepository(): ResultOf<List<Coin>> =
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