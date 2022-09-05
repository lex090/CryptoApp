package com.github.lex090.cryptoapp

import androidx.multidex.MultiDexApplication
import com.github.lex090.corefactory.CoreComponentDependenciesFactory
import com.github.lex090.cryptoapp.di.ApplicationComponent
import com.github.lex090.cryptoapp.di.DaggerApplicationComponent


class CryptoAppApplication : MultiDexApplication() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .factory()
            .create(
                coreComponentDependencies = CoreComponentDependenciesFactory.create()
            )
    }
}