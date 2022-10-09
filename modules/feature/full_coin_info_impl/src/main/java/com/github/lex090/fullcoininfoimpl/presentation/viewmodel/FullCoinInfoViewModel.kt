package com.github.lex090.fullcoininfoimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import com.github.lex090.baseui.presentation.viewmodel.entity.toFormattedPriceText
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import com.github.lex090.fullcoininfoimpl.domain.IGetCoinInfoUseCase
import com.github.lex090.fullcoininfoimpl.domain.IGetLiveTimePriceOfCoinFlowUseCase
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI.CoinInfoUiEntity
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI.toCoinInfoUiEntity
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FullCoinInfoViewModel(
    private val getCoinInfoUseCase: IGetCoinInfoUseCase,
    private val getLiveTimePriceOfCoinFlowUseCase: IGetLiveTimePriceOfCoinFlowUseCase,
    private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
    private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
) : ViewModel() {

    private val _mutableScreenStateFlow: MutableStateFlow<BaseUiState<UiStateEntity>> =
        MutableStateFlow(BaseUiState.Loading)
    val screenState: MutableStateFlow<BaseUiState<UiStateEntity>> = _mutableScreenStateFlow

    fun getCoinInfo(coinId: String) {
        viewModelScope.launch {
            try {
                val coinInfo = getCoinInfoUseCase.execute(coinId)
                _mutableScreenStateFlow.value =
                    BaseUiState.Success(coinInfo.toCoinInfoUiEntity())
                subscribeToRealTimePriceUpdating(coinId)
            } catch (e: Exception) {
                _mutableScreenStateFlow.value = BaseUiState.Error(e, e.message)
            }
        }
    }

    private suspend fun subscribeToRealTimePriceUpdating(coinId: String) {
        getLiveTimePriceOfCoinFlowUseCase
            .execute(coinId)
            .collect { price ->
                doWorkWithCoinEntity { coinInfoUiEntity ->
                    val newValue = coinInfoUiEntity.copy(
                        price = price.toFormattedPriceText()
                    )
                    _mutableScreenStateFlow.value = BaseUiState.Success(newValue)
                }
            }
    }

    fun clickOnAddCoinToFavorites() {
        viewModelScope.launch {
            doWorkWithCoinEntity { coinInfoUiEntity ->
                addCoinToFavoritesUseCase.execute(data = coinInfoUiEntity.originalData)
                _mutableScreenStateFlow.value =
                    BaseUiState.Success(coinInfoUiEntity.copy(isFavorite = true))
            }
        }
    }

    fun clickOnRemoveCoinFromFavorites() {
        viewModelScope.launch {
            doWorkWithCoinEntity { coinInfoUiEntity ->
                removeCoinFromFavoritesUseCase.execute(data = coinInfoUiEntity.originalData)
                _mutableScreenStateFlow.value =
                    BaseUiState.Success(coinInfoUiEntity.copy(isFavorite = false))
            }
        }
    }

    private suspend fun doWorkWithCoinEntity(
        action: suspend (CoinInfoUiEntity) -> Unit
    ) {
        val state = screenState.value as? BaseUiState.Success
        val data = state?.data as? CoinInfoUiEntity
        if (data == null) return else action.invoke(data)
    }

    fun clear() {
        viewModelScope.cancel()
    }

    class Factory @Inject constructor(
        private val getCoinInfoFlowUseCase: IGetCoinInfoUseCase,
        private val getLiveTimePriceOfCoinFlowUseCase: IGetLiveTimePriceOfCoinFlowUseCase,
        private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase,
        private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == FullCoinInfoViewModel::class.java)
            return FullCoinInfoViewModel(
                getCoinInfoUseCase = getCoinInfoFlowUseCase,
                getLiveTimePriceOfCoinFlowUseCase = getLiveTimePriceOfCoinFlowUseCase,
                addCoinToFavoritesUseCase = addCoinToFavoritesUseCase,
                removeCoinFromFavoritesUseCase = removeCoinFromFavoritesUseCase
            ) as T
        }
    }
}