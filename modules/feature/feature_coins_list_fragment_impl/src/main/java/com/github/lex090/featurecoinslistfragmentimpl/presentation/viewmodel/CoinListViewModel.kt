package com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basecoins.di.GetCoinsListUseCaseDependence
import com.github.lex090.basecoins.domain.entity.CoinsList
import com.github.lex090.basecoins.domain.usecases.IGetCoinsListUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.CoinUiEntity
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.toCoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinListViewModel(
    private val getCoinsListUseCase: IGetCoinsListUseCase,
    private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase
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

    fun onAddToFavoriteClick(coinUiEntity: CoinUiEntity) {
        viewModelScope.launch {
            addCoinToFavoritesUseCase.execute(coinUiEntity.toCoin())
        }
    }

    private suspend fun getCoinsFromRepository(): ResultOf<CoinsList> =
        getCoinsListUseCase.execute()


    class Factory @Inject constructor(
        @GetCoinsListUseCaseDependence
        private val getCoinsListUseCase: IGetCoinsListUseCase,
        private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CoinListViewModel::class.java)
            return CoinListViewModel(getCoinsListUseCase, addCoinToFavoritesUseCase) as T
        }
    }
}