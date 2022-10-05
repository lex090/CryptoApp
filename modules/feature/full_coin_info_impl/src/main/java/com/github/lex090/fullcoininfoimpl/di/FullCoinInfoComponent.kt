package com.github.lex090.fullcoininfoimpl.di

import com.github.lex090.corediapi.AppDependenciesProvider
import com.github.lex090.fullcoininfoimpl.presentation.view.FullCoinInfo
import dagger.Component

@Component(
    modules = [
        BaseFullCoinInfoModule::class
    ],
    dependencies = [
        AppDependenciesProvider::class
    ]
)
interface FullCoinInfoComponent {

    fun inject(screen: FullCoinInfo)

    @Component.Factory
    interface Factory {

        fun create(dependencies: AppDependenciesProvider): FullCoinInfoComponent
    }
}