package com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basecoins.di.GetCoinsListUseCaseDependence
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basecoins.domain.usecases.IGetCoinListFlowUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import com.github.lex090.baseui.presentation.viewmodel.entity.toCoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.toCoinUiEntityList
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

class CoinListViewModel(
    getCoinListFlowUseCase: IGetCoinListFlowUseCase,
    private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
    private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
) : ViewModel() {

    companion object {
        private const val STOP_TIMEOUT_WHILE_SUBSCRIBE = 5000L
    }

    val screenState: StateFlow<BaseUiState<UiStateEntity>> =
        getCoinListFlowUseCase
            .execute()
            .map(::processCoinsDataFlow)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(STOP_TIMEOUT_WHILE_SUBSCRIBE),
                BaseUiState.Loading
            )

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

    private fun processCoinsDataFlow(
        result: ResultOf<List<Coin>>
    ): BaseUiState<UiStateEntity> = when (result) {
        is ResultOf.Error -> {
            BaseUiState.Error(result.exception, result.message)
        }
        is ResultOf.Success -> {
            val coins = result.data.map { it.toCoinUiEntity() }.toCoinUiEntityList()
            BaseUiState.Success(coins)
        }
    }

    class Factory @Inject constructor(
        @GetCoinsListUseCaseDependence
        private val getCoinsListUseCase: IGetCoinListFlowUseCase,
        private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
        private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
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