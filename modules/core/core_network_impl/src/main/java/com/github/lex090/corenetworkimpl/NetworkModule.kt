package com.github.lex090.corenetworkimpl

import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL
import java.util.concurrent.TimeUnit

internal typealias ConnectionTimeout = Pair<Long, TimeUnit>
internal typealias CallTimeout = Pair<Long, TimeUnit>
internal typealias ReadTimeout = Pair<Long, TimeUnit>

@Module(
    includes = [
        NetworkModule::class,
        BindsNetworkModule::class,
    ]
)
object BaseNetworkModule

@Module
internal object NetworkModule {

    @Provides
    fun provideRetrofit(
        url: URL,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    fun provideUrl(baseUrlProvider: IBaseUrlProvider): URL = baseUrlProvider.provideBaseUrl()

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor,
        connectionTimeout: ConnectionTimeout,
        callTimeout: CallTimeout,
        readTimeout: ReadTimeout,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(connectionTimeout.first, connectionTimeout.second)
            .callTimeout(callTimeout.first, callTimeout.second)
            .readTimeout(readTimeout.first, readTimeout.second)
            .build()

    @Provides
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }

    @Provides
    fun provideConnectTimeoutSeconds(): ConnectionTimeout = 10L to TimeUnit.SECONDS

    @Provides
    fun provideCallTimeoutSeconds(): CallTimeout = 10L to TimeUnit.SECONDS

    @Provides
    fun provideReadTimeoutSeconds(): ReadTimeout = 10L to TimeUnit.SECONDS

    @Provides
    fun provideConverterFactory(): Converter.Factory =
        MoshiConverterFactory.create()

}

@Suppress("FunctionName")
@Module
internal interface BindsNetworkModule {

    @Binds
    fun bind_BaseUrlProviderImpl_to_IBaseUrlProvider(
        baseUrlProvider: BaseUrlProviderImpl
    ): IBaseUrlProvider

    @Binds
    fun bind_RemoteNetworkServiceGeneratorImpl_to_IRemoteNetworkServiceGenerator(
        remoteNetworkServiceGeneratorImpl: RemoteNetworkServiceGeneratorImpl
    ): IRemoteNetworkServiceGenerator
}