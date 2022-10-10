package com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.usecases.IGetFavoriteCoinsWithRemoteUpdatingUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import com.github.lex090.baseui.presentation.viewmodel.entity.toCoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.toCoinUiEntityList
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteCoinsViewModel(
    private val getFavoriteCoinsWithRemoteUpdatingUseCase: IGetFavoriteCoinsWithRemoteUpdatingUseCase,
    private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
) : ViewModel() {

    companion object {
        private const val STOP_TIMEOUT_WHILE_SUBSCRIBE = 5000L
    }

    val screenState: StateFlow<BaseUiState<UiStateEntity>> =
        getFavoriteCoinsWithRemoteUpdatingUseCase
            .execute()
            .map(::processFavoriteCoinsDataFlow)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(STOP_TIMEOUT_WHILE_SUBSCRIBE),
                BaseUiState.Loading
            )

    fun clickOnRemoveCoinFromFavorites(coin: Coin) {
        viewModelScope.launch {
            removeCoinFromFavoritesUseCase.execute(data = coin.copy(isFavorite = false))
        }
    }

    private fun processFavoriteCoinsDataFlow(
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
        private val getFavoriteCoinsWithRemoteUpdatingUseCase: IGetFavoriteCoinsWithRemoteUpdatingUseCase,
        private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == FavoriteCoinsViewModel::class.java)
            return FavoriteCoinsViewModel(
                getFavoriteCoinsWithRemoteUpdatingUseCase = getFavoriteCoinsWithRemoteUpdatingUseCase,
                removeCoinFromFavoritesUseCase = removeCoinFromFavoritesUseCase
            ) as T
        }
    }
}