package com.github.lex090.corenetworkapi

import kotlin.reflect.KClass

interface RemoteNetworkServiceGenerator {

    fun <ServiceType : Any> create(remoteService: KClass<ServiceType>): ServiceType
}