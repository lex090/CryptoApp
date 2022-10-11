package com.github.lex090.corenetworkimpl

import com.github.lex090.corediapi.ApplicationScope
import com.github.lex090.corenetworkapi.IRemoteNetworkServiceGenerator
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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

@Module(
    includes = [
        NetworkModule::class,
        BindsNetworkModule::class,
    ]
)
object BaseNetworkModule

@Module
internal object NetworkModule {

    @ApplicationScope
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
    fun provideUrl(baseUrlProvider: IUrlProvider): URL = baseUrlProvider.provideUrl()

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor,
        @ConnectionTimeout connectionTimeout: Timeout,
        @CallTimeout callTimeout: Timeout,
        @ReadTimeout readTimeout: Timeout,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(connectionTimeout.timeout, connectionTimeout.unit)
            .callTimeout(callTimeout.timeout, callTimeout.unit)
            .readTimeout(readTimeout.timeout, readTimeout.unit)
            .build()

    @Provides
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @ConnectionTimeout
    fun provideConnectTimeoutSeconds(): Timeout = Timeout(timeout = 15, unit = TimeUnit.SECONDS)

    @Provides
    @CallTimeout
    fun provideCallTimeoutSeconds(): Timeout = Timeout(timeout = 15, unit = TimeUnit.SECONDS)

    @Provides
    @ReadTimeout
    fun provideReadTimeoutSeconds(): Timeout = Timeout(timeout = 15, unit = TimeUnit.SECONDS)

    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideConverterFactory(moshi: Moshi): Converter.Factory =
        MoshiConverterFactory.create(moshi)
}

@Suppress("FunctionName")
@Module
internal interface BindsNetworkModule {

    @Binds
    fun bind_BaseUrlProviderImpl_to_IBaseUrlProvider(
        baseUrlProvider: BaseUrlProviderImpl
    ): IUrlProvider

    @Binds
    fun bind_RemoteNetworkServiceGeneratorImpl_to_IRemoteNetworkServiceGenerator(
        remoteNetworkServiceGeneratorImpl: RemoteNetworkServiceGeneratorImpl
    ): IRemoteNetworkServiceGenerator
}