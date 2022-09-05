package com.github.lex090.cryptoapp.dependencies

import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxAppcompatVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxConstraintLayoutVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxCoreKtxVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxEspressoVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxJunitVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.dagger2Version
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.junitVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.materialVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.moshiVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.okHttp3LoggingInterceptorVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.okHttp3Version
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.retrofitConverterMoshiVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.retrofitVersion

object Dependencies {

    private object Versions {

        const val androidxCoreKtxVersion = "1.7.0"
        const val androidxAppcompatVersion = "1.5.0"
        const val materialVersion = "1.6.1"
        const val androidxConstraintLayoutVersion = "1.6.1"
        const val junitVersion = "4.13.2"
        const val androidxJunitVersion = "1.1.3"
        const val androidxEspressoVersion = "3.4.0"
        const val retrofitVersion = "2.9.0"
        const val okHttp3Version = "4.10.0"
        const val okHttp3LoggingInterceptorVersion = "4.10.0"
        const val dagger2Version = "2.43.2"
        const val retrofitConverterMoshiVersion = "2.4.0"
        const val moshiVersion = "1.13.0"
    }

    object ProjectModules {

        const val coreNetworkApi = ":modules:core:core_network_api"
    }

    object Libraries {

        const val androidxCoreKtx = "androidx.core:core-ktx:$androidxCoreKtxVersion"
        const val androidxAppcompat = "androidx.appcompat:appcompat:$androidxAppcompatVersion"
        const val material = "com.google.android.material:material:$materialVersion"
        const val androidxConstraintLayout =
            "androidx.constraintlayout:constraintlayout:$androidxConstraintLayoutVersion"
        const val junit = "junit:junit:$junitVersion"
        const val androidxJunit = "androidx.test.ext:junit:$androidxJunitVersion"
        const val androidxEspresso = "androidx.test.espresso:espresso-core:$androidxEspressoVersion"


        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitConverterMoshi =
            "com.squareup.retrofit2:converter-moshi:$retrofitConverterMoshiVersion"
        const val moshi = "com.squareup.moshi:moshi:$moshiVersion"

        const val okHttp3 = "com.squareup.okhttp3:okhttp:$okHttp3Version"
        const val okHttp3LoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:$okHttp3LoggingInterceptorVersion"

        const val dagger2 = "com.google.dagger:dagger:$dagger2Version"
    }
}

object AppConfiguration {

    const val compileSdk = 32
    const val applicationId = "com.github.lex090.cryptoapp"
    const val minSdk = 23
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0"
}

