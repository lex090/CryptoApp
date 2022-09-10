package com.github.lex090.featurecoinslistfragmentimpl.presentation.view.adapters

import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.CoinUiEntity
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

interface ICoinListItemAdapterFactory {

    fun createCommonCoinListItemAdapterFactory(
        addToFavoriteClickListener: (Int, CoinUiEntity, Boolean) -> Unit
    ): AdapterDelegate<List<DisplayableItem>>
}