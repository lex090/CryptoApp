package com.github.lex090.baseui.presentation.view.adapters

import com.github.lex090.baseui.presentation.viewmodel.entity.CoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

interface ICoinListItemAdapterFactory {

    fun createCommonCoinListItemAdapterFactory(
        onCoinItemClick: (coinUiEntity: CoinUiEntity) -> Unit,
        addCoinToFavoritesClickListener: (coinUiEntity: CoinUiEntity) -> Unit,
        removeCoinFromFavoritesListener: (coinUiEntity: CoinUiEntity) -> Unit,
    ): AdapterDelegate<List<DisplayableItem>>
}