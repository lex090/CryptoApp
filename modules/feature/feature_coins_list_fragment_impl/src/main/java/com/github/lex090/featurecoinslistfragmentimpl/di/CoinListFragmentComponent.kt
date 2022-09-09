package com.github.lex090.featurecoinslistfragmentimpl.di

import dagger.Component

@Component(
    modules = [
        BaseCoinListFragmentModule::class
    ],
    dependencies = [

    ]
)
interface CoinListFragmentComponent {

    @Component.Factory
    interface Factory {

        fun create(): CoinListFragmentComponent
    }
}