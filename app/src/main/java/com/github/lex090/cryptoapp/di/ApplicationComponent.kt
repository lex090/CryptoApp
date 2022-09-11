package com.github.lex090.cryptoapp.di

import com.github.lex090.corediapi.AppDependenciesProvider
import com.github.lex090.corediapi.ApplicationScope
import com.github.lex090.corediapi.CoreComponentDependencies
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        BaseApplicationModule::class
    ],
    dependencies = [
        CoreComponentDependencies::class
    ]
)
interface ApplicationComponent : AppDependenciesProvider {

    @Component.Factory
    interface Factory {

        fun create(
            coreComponentDependencies: CoreComponentDependencies
        ): ApplicationComponent
    }
}