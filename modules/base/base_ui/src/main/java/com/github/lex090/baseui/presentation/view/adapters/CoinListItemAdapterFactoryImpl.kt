package com.github.lex090.baseui.presentation.view.adapters

import com.github.lex090.baseui.databinding.ItemSmallCoinInfoBinding
import com.github.lex090.baseui.presentation.view.checkFavorite
import com.github.lex090.baseui.presentation.view.loadCoinImageToIV
import com.github.lex090.baseui.presentation.viewmodel.entity.CoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import javax.inject.Inject

internal class CoinListItemAdapterFactoryImpl @Inject constructor() : ICoinListItemAdapterFactory {

    override fun createCommonCoinListItemAdapterFactory(
        onCoinItemClick: (coinUiEntity: CoinUiEntity) -> Unit,
        addCoinToFavoritesClickListener: (coinUiEntity: CoinUiEntity) -> Unit,
        removeCoinFromFavoritesListener: (coinUiEntity: CoinUiEntity) -> Unit
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
                    removeCoinFromFavoritesListener(item)
                else
                    addCoinToFavoritesClickListener(item)
            }

            binding.root.setOnClickListener {
                onCoinItemClick(item)
            }

            bind {
                if (it.isNotEmpty()) {
                    val isFavorite = it.first() as Boolean
                    binding.btnFavorite.checkFavorite(isFavorite)
                } else {
                    bindItem()
                }
            }
        }

    private fun AdapterDelegateViewBindingViewHolder<CoinUiEntity, ItemSmallCoinInfoBinding>.bindItem() {
        binding.tvCoinRang.text = item.rang
        binding.tvCoinSymbol.text = item.symbol
        binding.tvCoinName.text = item.name
        binding.tvCoinPrice.text = item.price
        binding.tvCoinPricePercentage.text = item.priceChanging
        binding.ivCoin.loadCoinImageToIV(item.imageUrl)
        binding.btnFavorite.checkFavorite(item.isFavorite)
    }
}