package com.github.lex090.corediapi

import com.github.lex090.coredbapi.data.dao.FavoriteCoinsDao
import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator
import okhttp3.OkHttpClient
import kotlin.coroutines.CoroutineContext

interface CoreComponentDependencies {

    val remoteNetworkServiceGenerator: IRemoteNetworkServiceGenerator

    val favoriteCoinDao: FavoriteCoinsDao

    val dispatcherIo: CoroutineContext

    val okHttpClient: OkHttpClient
}