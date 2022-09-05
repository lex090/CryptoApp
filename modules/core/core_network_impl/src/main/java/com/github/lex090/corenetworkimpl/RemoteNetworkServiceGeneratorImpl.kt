package com.github.lex090.corenetworkimpl

import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator
import retrofit2.Retrofit
import javax.inject.Inject

internal class RemoteNetworkServiceGeneratorImpl @Inject constructor(
    private val retrofit: Retrofit
) : IRemoteNetworkServiceGenerator {

    override fun <ServiceType : Any> create(
        remoteService: Class<ServiceType>
    ): ServiceType =
        retrofit.create(remoteService)
}