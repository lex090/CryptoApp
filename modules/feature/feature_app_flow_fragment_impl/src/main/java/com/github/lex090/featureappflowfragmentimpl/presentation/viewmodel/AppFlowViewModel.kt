package com.github.lex090.featureappflowfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.lex090.basefavoriteimpl.domain.usecases.IFavoritesCoinsCountChangedUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AppFlowViewModel(
    private val favoritesCoinsCountChangedUseCase: IFavoritesCoinsCountChangedUseCase
) : ViewModel() {

    val favoritesCoinsCount: StateFlow<Int> = favoritesCoinsCountChangedUseCase
        .execute()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    class Factory @Inject constructor(
        private val favoritesCoinsCountChangedUseCase: IFavoritesCoinsCountChangedUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == AppFlowViewModel::class.java)
            return AppFlowViewModel(
                favoritesCoinsCountChangedUseCase = favoritesCoinsCountChangedUseCase
            ) as T
        }
    }
}