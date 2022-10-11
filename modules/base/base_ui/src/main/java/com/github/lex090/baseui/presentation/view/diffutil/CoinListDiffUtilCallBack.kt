package com.github.lex090.baseui.presentation.view.diffutil

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.github.lex090.baseui.presentation.viewmodel.entity.CoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.DisplayableItem
import javax.inject.Inject

class CoinListDiffUtilCallBack @Inject constructor() : DiffUtil.ItemCallback<DisplayableItem>() {
    override fun areItemsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
        return when {
            oldItem is CoinUiEntity && newItem is CoinUiEntity -> {
                oldItem.name == newItem.name
            }
            else -> false
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: DisplayableItem,
        newItem: DisplayableItem
    ): Boolean {
        return when {
            oldItem is CoinUiEntity && newItem is CoinUiEntity -> {
                oldItem == newItem
            }
            else -> false
        }
    }

    override fun getChangePayload(oldItem: DisplayableItem, newItem: DisplayableItem): Any? {
        return when {
            oldItem is CoinUiEntity && newItem is CoinUiEntity -> {
                if (oldItem.isFavorite != newItem.isFavorite) {
                    newItem.isFavorite
                } else {
                    super.getChangePayload(oldItem, newItem)
                }
            }
            else -> super.getChangePayload(oldItem, newItem)

        }
    }
}