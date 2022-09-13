package com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.basefavoriteimpl.domain.usecases.IGetFavoriteCoinsWithRemoteUpdatingUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteCoinsViewModel(
    private val getFavoriteCoinsWithRemoteUpdatingUseCase: IGetFavoriteCoinsWithRemoteUpdatingUseCase,
    private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase,
) : ViewModel() {

    val favoriteCoinsList: StateFlow<List<Coin>> =
        getFavoriteCoinsWithRemoteUpdatingUseCase
            .execute()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                listOf()
            )

    fun clickOnRemoveCoinFromFavorites(position: Int, coin: Coin) {
        viewModelScope.launch {
            removeCoinFromFavoritesUseCase.execute(data = coin)
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