package com.github.lex090.baseui.presentation.view.adapters

import com.github.lex090.baseui.presentation.view.entity.CoinUiEntity
import com.github.lex090.baseui.presentation.view.entity.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

interface ICoinListItemAdapterFactory {

    fun createCommonCoinListItemAdapterFactory(
        addCoinToFavoritesClickListener: (position: Int, coinUiEntity: CoinUiEntity) -> Unit,
        removeCoinFromFavoritesListener: (position: Int, coinUiEntity: CoinUiEntity) -> Unit,
    ): AdapterDelegate<List<DisplayableItem>>
}