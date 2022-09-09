package com.github.lex090.corediimpl

import com.github.lex090.corediapi.ApplicationScope
import com.github.lex090.corediapi.CoreComponentDependencies
import com.github.lex090.corenetworkimpl.BaseNetworkModule
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        BaseNetworkModule::class
    ]
)
interface CoreComponent : CoreComponentDependencies {

    @Component.Factory
    interface Factory {

        fun create(): CoreComponent
    }
}