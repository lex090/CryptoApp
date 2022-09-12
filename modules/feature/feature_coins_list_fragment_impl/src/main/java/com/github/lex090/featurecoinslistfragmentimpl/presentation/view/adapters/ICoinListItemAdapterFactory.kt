package com.github.lex090.featurecoinslistfragmentimpl.presentation.view.adapters

import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.CoinUiEntity
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

interface ICoinListItemAdapterFactory {

    fun createCommonCoinListItemAdapterFactory(
        addCoinToFavoritesClickListener: (position: Int, coinUiEntity: CoinUiEntity) -> Unit,
        removeCoinFromFavoritesListener: (position: Int, coinUiEntity: CoinUiEntity) -> Unit,
    ): AdapterDelegate<List<DisplayableItem>>
}