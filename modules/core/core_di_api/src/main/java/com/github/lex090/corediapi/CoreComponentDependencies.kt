package com.github.lex090.corediapi

import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator

interface CoreComponentDependencies {

    val remoteNetworkServiceGenerator: IRemoteNetworkServiceGenerator

    val favoriteCoinDao: FavoriteCoinsDao
}