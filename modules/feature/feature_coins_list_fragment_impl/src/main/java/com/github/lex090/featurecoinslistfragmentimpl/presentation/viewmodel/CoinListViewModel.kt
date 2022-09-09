package com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.coreapi.domain.IBaseUseCase
import com.github.lex090.corenetworkapi.ResultOf
import com.github.lex090.featurecoinslistfragmentimpl.domain.entity.CoinsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinListViewModel(
    private val getCoinsListUseCase: IBaseUseCase<ResultOf<CoinsList>>
) : ViewModel() {

    private val _mutableCoinsListStateFlow: MutableStateFlow<ResultOf.Success<CoinsList>> =
        MutableStateFlow(
            ResultOf.Success(
                CoinsList(coinsList = listOf())
            )
        )
    val coinsList: StateFlow<ResultOf.Success<CoinsList>> = _mutableCoinsListStateFlow


    fun onViewsInit() {
        viewModelScope.launch {
            when (val result = getCoinsFromRepository()) {
                is ResultOf.Error -> {

                }
                is ResultOf.Success -> {
                    _mutableCoinsListStateFlow.value = result
                }
            }
        }
    }

    private suspend fun getCoinsFromRepository(): ResultOf<CoinsList> =
        getCoinsListUseCase.execute()


    class Factory @Inject constructor(
        private val getCoinsListUseCase: IBaseUseCase<ResultOf<CoinsList>>,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CoinListViewModel::class.java)
            return CoinListViewModel(getCoinsListUseCase) as T
        }
    }
}