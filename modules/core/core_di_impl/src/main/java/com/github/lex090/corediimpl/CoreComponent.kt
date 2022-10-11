package com.github.lex090.corediimpl

import android.content.Context
import com.github.lex090.coredbapi.data.dao.FavoriteCoinsDao
import com.github.lex090.coredbimpl.di.DatabaseModule
import com.github.lex090.corediapi.ApplicationContext
import com.github.lex090.corediapi.ApplicationScope
import com.github.lex090.corediapi.CoreComponentDependencies
import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator
import com.github.lex090.corenetworkimpl.BaseNetworkModule
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import kotlin.coroutines.CoroutineContext

@ApplicationScope
@Component(
    modules = [
        BaseNetworkModule::class,
        DatabaseModule::class,
        DispatchersModule::class
    ]
)
interface CoreComponent : CoreComponentDependencies {

    override val remoteNetworkServiceGenerator: IRemoteNetworkServiceGenerator

    override val favoriteCoinDao: FavoriteCoinsDao

    override val dispatcherIo: CoroutineContext

    override val okHttpClient: OkHttpClient

    @Component.Factory
    interface Factory {

        fun create(
            @ApplicationContext
            @BindsInstance
            context: Context
        ): CoreComponent
    }
}