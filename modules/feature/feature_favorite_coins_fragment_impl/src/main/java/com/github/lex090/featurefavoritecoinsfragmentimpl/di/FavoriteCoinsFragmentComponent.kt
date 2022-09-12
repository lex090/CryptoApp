package com.github.lex090.featurefavoritecoinsfragmentimpl.di

import com.github.lex090.corediapi.AppDependenciesProvider
import com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.view.FavoriteCoinsFragment
import dagger.Component


@Component(
    modules = [
        BaseFavoriteCoinsFragmentModule::class
    ],
    dependencies = [
        AppDependenciesProvider::class
    ]
)
interface FavoriteCoinsFragmentComponent {

    fun inject(fragment: FavoriteCoinsFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: AppDependenciesProvider): FavoriteCoinsFragmentComponent
    }
}