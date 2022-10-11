package com.github.lex090.featureappflowfragmentimpl.di

import com.github.lex090.basefavoriteimpl.di.BaseFavoriteModule
import com.github.lex090.corediapi.AppDependenciesProvider
import com.github.lex090.featureappflowfragmentimpl.presentation.view.AppFlowFragment
import dagger.Component


@Component(
    modules = [
        BaseFavoriteModule::class
    ],
    dependencies = [
        AppDependenciesProvider::class
    ]
)
interface AppFlowFragmentComponent {

    fun inject(fragment: AppFlowFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: AppDependenciesProvider): AppFlowFragmentComponent
    }
}