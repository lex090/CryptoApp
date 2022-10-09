package com.github.lex090.fullcoininfoimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import com.github.lex090.fullcoininfoimpl.domain.IGetCoinInfoUseCase
import com.github.lex090.fullcoininfoimpl.domain.IGetLiveTimePriceOfCoinFlowUseCase
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI.toCoinInfoUiEntity
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FullCoinInfoViewModel(
    private val getCoinInfoUseCase: IGetCoinInfoUseCase,
    private val getLiveTimePriceOfCoinFlowUseCase: IGetLiveTimePriceOfCoinFlowUseCase
) : ViewModel() {

    private val _mutableScreenStateFlow: MutableStateFlow<BaseUiState<UiStateEntity>> =
        MutableStateFlow(BaseUiState.Loading)
    val screenState: MutableStateFlow<BaseUiState<UiStateEntity>> = _mutableScreenStateFlow

    fun getCoinInfo(coinId: String) {
        viewModelScope.launch {
            try {
                val coinInfo = getCoinInfoUseCase.execute(coinId)
                _mutableScreenStateFlow.value = BaseUiState.Success(coinInfo.toCoinInfoUiEntity())
                subscribeToRealTimePriceUpdating(coinId)
            } catch (e: Exception) {
                _mutableScreenStateFlow.value = BaseUiState.Error(e, e.message)
            }
        }
    }

    private suspend fun subscribeToRealTimePriceUpdating(coinId: String) {
        getLiveTimePriceOfCoinFlowUseCase
            .execute(coinId)
            .collect {
                //                    _mutableCoinInfoStateFlow.value = coinInfo.copy(price = it)
            }
    }

    fun clear() {
        viewModelScope.cancel()
    }

    class Factory @Inject constructor(
        private val getCoinInfoFlowUseCase: IGetCoinInfoUseCase,
        private val getLiveTimePriceOfCoinFlowUseCase: IGetLiveTimePriceOfCoinFlowUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == FullCoinInfoViewModel::class.java)
            return FullCoinInfoViewModel(
                getCoinInfoUseCase = getCoinInfoFlowUseCase,
                getLiveTimePriceOfCoinFlowUseCase = getLiveTimePriceOfCoinFlowUseCase
            ) as T
        }
    }
}