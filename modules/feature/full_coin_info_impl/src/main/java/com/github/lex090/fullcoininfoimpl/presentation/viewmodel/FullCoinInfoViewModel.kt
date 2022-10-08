package com.github.lex090.fullcoininfoimpl.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.fullcoininfoimpl.domain.IGetCoinInfoUseCase
import com.github.lex090.fullcoininfoimpl.domain.IGetLiveTimePriceOfCoinFlowUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FullCoinInfoViewModel(
    private val getCoinInfoFlowUseCase: IGetCoinInfoUseCase,
    private val getLiveTimePriceOfCoinFlowUseCase: IGetLiveTimePriceOfCoinFlowUseCase
) : ViewModel() {

    private val _mutableCoinInfoStateFlow: MutableStateFlow<Coin?> = MutableStateFlow(null)
    val coinInfo: StateFlow<Coin?> = _mutableCoinInfoStateFlow

    fun getCoinInfo(coinId: String) {
        viewModelScope.launch {
            try {
                val coinInfo = getCoinInfoFlowUseCase.execute(coinId)
                Log.i("myDebug", "getCoinInfo: $coinInfo")
                _mutableCoinInfoStateFlow.value = coinInfo
                getLiveTimePriceOfCoinFlowUseCase.execute(coinId).collect {
//                    _mutableCoinInfoStateFlow.value = coinInfo.copy(price = it)
                }
            } catch (e: Exception) {
                Log.i("myDebug", "getCoinInfo: ${e.localizedMessage}")
            }
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
                getCoinInfoFlowUseCase = getCoinInfoFlowUseCase,
                getLiveTimePriceOfCoinFlowUseCase = getLiveTimePriceOfCoinFlowUseCase
            ) as T
        }
    }
}