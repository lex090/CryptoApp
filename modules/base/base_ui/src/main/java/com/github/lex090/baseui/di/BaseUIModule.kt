package com.github.lex090.baseui.di

import androidx.recyclerview.widget.DiffUtil
import com.github.lex090.baseui.presentation.view.adapters.CoinListItemAdapterFactoryImpl
import com.github.lex090.baseui.presentation.view.adapters.ICoinListItemAdapterFactory
import com.github.lex090.baseui.presentation.view.diffutil.CoinListDiffUtilCallBack
import com.github.lex090.baseui.presentation.view.entity.DisplayableItem
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        UIModule::class
    ]
)
object BaseUIModule


@Suppress("FunctionName")
@Module
internal interface UIModule {

    @Binds
    fun bind_CoinListItemAdapterFactoryImpl_to_ICoinListItemAdapterFactory(
        coinListItemAdapterFactoryImpl: CoinListItemAdapterFactoryImpl
    ): ICoinListItemAdapterFactory

    @Binds
    fun binds_CoinListDiffUtilCallBack_To_DiffUtilItemCallback(
        coinListDiffUtilCallBack: CoinListDiffUtilCallBack
    ): DiffUtil.ItemCallback<DisplayableItem>
}