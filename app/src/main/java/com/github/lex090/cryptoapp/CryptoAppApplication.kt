package com.github.lex090.cryptoapp

import androidx.multidex.MultiDexApplication
import com.github.lex090.corediapi.AppDependenciesProvider
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.corefactory.CoreComponentDependenciesFactory
import com.github.lex090.cryptoapp.di.DaggerApplicationComponent


class CryptoAppApplication : MultiDexApplication(), AppDependenciesProvidersHolder {

    private var aggregatingProvider: AppDependenciesProvider? = null

    override fun getProvider(): AppDependenciesProvider {
        return aggregatingProvider ?: DaggerApplicationComponent
            .factory()
            .create(
                coreComponentDependencies = CoreComponentDependenciesFactory
                    .create(applicationContext = this)
            ).apply {
                aggregatingProvider = this
            }
    }
}