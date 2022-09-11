package com.github.lex090.featurecoinslistfragmentimpl.di

import com.github.lex090.basecoins.di.BaseCoinsModule
import com.github.lex090.corediapi.AppDependenciesProvider
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.CoinsListFragment
import dagger.Component

@Component(
    modules = [
        BaseCoinListFragmentModule::class,
        BaseCoinsModule::class
    ],
    dependencies = [
        AppDependenciesProvider::class
    ]
)
interface CoinListFragmentComponent {

    fun inject(fragment: CoinsListFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: AppDependenciesProvider): CoinListFragmentComponent
    }
}