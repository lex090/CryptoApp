package com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class FavoriteCoinsViewModel() : ViewModel() {


    class Factory @Inject constructor() : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == FavoriteCoinsViewModel::class.java)
            return FavoriteCoinsViewModel() as T
        }
    }
}