package com.github.lex090.baseui.presentation.view.adapters

import androidx.core.content.ContextCompat
import com.github.lex090.baseui.R
import com.github.lex090.baseui.databinding.ItemSmallCoinInfoBinding
import com.github.lex090.baseui.presentation.view.entity.CoinUiEntity
import com.github.lex090.baseui.presentation.view.entity.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import javax.inject.Inject

internal class CoinListItemAdapterFactoryImpl @Inject constructor() : ICoinListItemAdapterFactory {

    override fun createCommonCoinListItemAdapterFactory(
        onCoinItemClick: (coinUiEntity: CoinUiEntity) -> Unit,
        addCoinToFavoritesClickListener: (position: Int, coinUiEntity: CoinUiEntity) -> Unit,
        removeCoinFromFavoritesListener: (position: Int, coinUiEntity: CoinUiEntity) -> Unit
    ): AdapterDelegate<List<DisplayableItem>> =
        adapterDelegateViewBinding<CoinUiEntity, DisplayableItem, ItemSmallCoinInfoBinding>(
            { layoutInflater, root ->
                ItemSmallCoinInfoBinding.inflate(
                    layoutInflater,
                    root,
                    false
                )
            }
        ) {
            binding.btnFavorite.setOnClickListener {
                if (item.isFavorite)
                    removeCoinFromFavoritesListener(adapterPosition, item)
                else
                    addCoinToFavoritesClickListener(adapterPosition, item)
            }

            binding.root.setOnClickListener {
                onCoinItemClick(item)
            }

            bind {
                if (it.isNotEmpty()) {
                    val item = it.first() as Boolean
                    checkFavorite(item)
                } else {
                    bindItem()
                }
            }
        }

    private fun AdapterDelegateViewBindingViewHolder<CoinUiEntity, ItemSmallCoinInfoBinding>.bindItem() {
        binding.tvCoinName.text = item.name
        binding.tvCoinRang.text = "#${item.position.toString()}"
        binding.tvCoinPrice.text = "${item.price.toString()}$"
        checkFavorite(item.isFavorite)
    }

    private fun AdapterDelegateViewBindingViewHolder<CoinUiEntity, ItemSmallCoinInfoBinding>.checkFavorite(
        item: Boolean
    ) {
        if (item) {
            binding.btnFavorite.background =
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_24)
        } else {
            binding.btnFavorite.background = ContextCompat.getDrawable(
                context,
                R.drawable.ic_baseline_star_outline_24
            )
        }
    }
}