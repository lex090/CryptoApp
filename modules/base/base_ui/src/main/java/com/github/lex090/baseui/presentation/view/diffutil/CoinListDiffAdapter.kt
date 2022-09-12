package com.github.lex090.baseui.presentation.view.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.github.lex090.baseui.presentation.view.entity.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CoinListDiffAdapter @AssistedInject constructor(
    diffUtil: DiffUtil.ItemCallback<DisplayableItem>,
    @Assisted("adapterManager") adapterDelegatesManager: AdapterDelegatesManager<List<DisplayableItem>>
) : AsyncListDifferDelegationAdapter<DisplayableItem>(
    diffUtil,
    adapterDelegatesManager
) {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("adapterManager")
            adapterDelegatesManager: AdapterDelegatesManager<List<DisplayableItem>>
        ): CoinListDiffAdapter
    }
}