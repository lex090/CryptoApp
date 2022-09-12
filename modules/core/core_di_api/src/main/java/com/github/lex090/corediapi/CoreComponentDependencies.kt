package com.github.lex090.corediapi

import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator
import kotlin.coroutines.CoroutineContext

interface CoreComponentDependencies {

    val remoteNetworkServiceGenerator: IRemoteNetworkServiceGenerator

    val favoriteCoinDao: FavoriteCoinsDao

    val dispatcherIo: CoroutineContext
}