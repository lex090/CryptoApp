package com.github.lex090.corediimpl

import android.content.Context
import com.github.lex090.coredbimpl.di.DatabaseModule
import com.github.lex090.corediapi.ApplicationContext
import com.github.lex090.corediapi.ApplicationScope
import com.github.lex090.corediapi.CoreComponentDependencies
import com.github.lex090.corenetworkimpl.BaseNetworkModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        BaseNetworkModule::class,
        DatabaseModule::class
    ]
)
interface CoreComponent : CoreComponentDependencies {

    @Component.Factory
    interface Factory {

        fun create(
            @ApplicationContext
            @BindsInstance
            context: Context
        ): CoreComponent
    }
}