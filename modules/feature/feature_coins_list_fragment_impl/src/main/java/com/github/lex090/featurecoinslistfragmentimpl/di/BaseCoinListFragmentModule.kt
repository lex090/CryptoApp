package com.github.lex090.featurecoinslistfragmentimpl.di

import androidx.recyclerview.widget.DiffUtil
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.DisplayableItem
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.adapters.CoinListItemAdapterFactoryImpl
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.adapters.ICoinListItemAdapterFactory
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.diffutil.CoinListDiffUtilCallBack
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        CoinListFragmentBindsModule::class
    ]
)
object BaseCoinListFragmentModule


@Suppress("FunctionName")
@Module
interface CoinListFragmentBindsModule {

    @Binds
    fun bind_CoinListItemAdapterFactoryImpl_to_ICoinListItemAdapterFactory(
        coinListItemAdapterFactoryImpl: CoinListItemAdapterFactoryImpl
    ): ICoinListItemAdapterFactory

    @Binds
    fun binds_CoinListDiffUtilCallBack_To_DiffUtilItemCallback(
        coinListDiffUtilCallBack: CoinListDiffUtilCallBack
    ): DiffUtil.ItemCallback<DisplayableItem>
}