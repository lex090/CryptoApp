package com.github.lex090.cryptoapp.dependencies

import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.activityKtxVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.adapterDelegatesVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidArchCoreCoreTestingVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidXCoreTestingVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxAppcompatVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxConstraintLayoutVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxCoreKtxVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxEspressoVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxJunitVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.coroutinesTestingVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.dagger2Version
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.facebookShimmerVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.fragmentKtxVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.fragmentTestingVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.junitVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.kotlinxCoroutinesVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.materialVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.mockitoCoreVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.mockitoKotlinVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.mockitoVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.moshiVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.multidexVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.navigationVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.okHttp3LoggingInterceptorVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.okHttp3Version
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.picassoVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.retrofitConverterMoshiVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.retrofitVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.robolectricVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.roomVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.scarletVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.truthVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.turbineVersion

object Dependencies {

    private object Versions {

        const val kotlinxCoroutinesVersion = "1.6.4"
        const val androidxCoreKtxVersion = "1.7.0"
        const val androidxAppcompatVersion = "1.5.0"
        const val materialVersion = "1.6.1"
        const val androidxConstraintLayoutVersion = "1.6.1"
        const val junitVersion = "4.13.2"
        const val mockitoCoreVersion = "4.8.0"
        const val mockitoVersion = "2.13.0"
        const val mockitoKotlinVersion = "4.0.0"
        const val coroutinesTestingVersion = kotlinxCoroutinesVersion
        const val turbineVersion = "0.11.0"
        const val truthVersion = "1.0.1"
        const val fragmentTestingVersion = "1.5.2"
        const val androidXCoreTestingVersion = "1.4.0"
        const val robolectricVersion = "4.8"
        const val androidArchCoreCoreTestingVersion = "1.0.0"
        const val androidxJunitVersion = "1.1.3"
        const val androidxEspressoVersion = "3.4.0"
        const val retrofitVersion = "2.9.0"
        const val okHttp3Version = "4.10.0"
        const val okHttp3LoggingInterceptorVersion = "4.10.0"
        const val dagger2Version = "2.43.2"
        const val retrofitConverterMoshiVersion = "2.4.0"
        const val moshiVersion = "1.13.0"
        const val multidexVersion = "2.0.1"
        const val navigationVersion = "2.5.1"
        const val activityKtxVersion = "1.5.1"
        const val fragmentKtxVersion = "1.5.2"
        const val adapterDelegatesVersion = "4.3.2"
        const val roomVersion = "2.4.3"
        const val scarletVersion = "0.1.12"
        const val facebookShimmerVersion = "0.5.0"
        const val picassoVersion = "2.8"
    }

    object ProjectModules {

        const val coreNetworkApi = ":modules:core:core_network_api"
        const val coreNetworkImpl = ":modules:core:core_network_impl"

        const val coreDiApi = ":modules:core:core_di_api"
        const val coreDiImpl = ":modules:core:core_di_impl"

        const val coreFactory = ":modules:core:core_factory"

        const val coreApi = ":modules:core:core_api"

        const val coreDbApi = ":modules:core:core_db_api"
        const val coreDbImpl = ":modules:core:core_db_impl"

        const val baseFavorite = ":modules:base:base_favorite"

        const val baseCoins = ":modules:base:base_coins"

        const val baseUI = ":modules:base:base_ui"

        const val featureAppActivityApi = ":modules:feature:feature_app_activity_api"
        const val featureAppActivityImpl = ":modules:feature:feature_app_activity_impl"

        const val featureAppFlowFragmentApi = ":modules:feature:feature_app_flow_fragment_api"
        const val featureAppFlowFragmentImpl = ":modules:feature:feature_app_flow_fragment_impl"

        const val featureCoinsListFragmentApi = ":modules:feature:feature_coins_list_fragment_api"
        const val featureCoinsListFragmentImpl = ":modules:feature:feature_coins_list_fragment_impl"

        const val featureFavoriteCoinsFragmentApi =
            ":modules:feature:feature_favorite_coins_fragment_api"
        const val featureFavoriteCoinsFragmentImpl =
            ":modules:feature:feature_favorite_coins_fragment_impl"

        const val featureFullCoinInfoApi = ":modules:feature:full_coin_info_api"
        const val featureFullCoinInfoImpl = ":modules:feature:full_coin_info_impl"
    }

    object Libraries {

        const val kotlinxCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion"

        const val androidxCoreKtx = "androidx.core:core-ktx:$androidxCoreKtxVersion"
        const val androidxAppcompat = "androidx.appcompat:appcompat:$androidxAppcompatVersion"
        const val material = "com.google.android.material:material:$materialVersion"
        const val androidxConstraintLayout =
            "androidx.constraintlayout:constraintlayout:$androidxConstraintLayoutVersion"

        const val junit = "junit:junit:$junitVersion"
        const val androidxJunit = "androidx.test.ext:junit:$androidxJunitVersion"
        const val androidxEspresso = "androidx.test.espresso:espresso-core:$androidxEspressoVersion"
        const val mockitoCore = "org.mockito:mockito-core:$mockitoCoreVersion"
        const val mockitoInline = "org.mockito:mockito-inline:$mockitoVersion"

        const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion"
        const val coroutinesTesting =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesTestingVersion"
        const val turbine = "app.cash.turbine:turbine:$turbineVersion"
        const val truth = "com.google.truth:truth:$truthVersion"
        const val androidArchCoreCoreTesting =
            "android.arch.core:core-testing:$androidArchCoreCoreTestingVersion"
        const val fragmentTesting = "androidx.fragment:fragment-testing:$fragmentTestingVersion"
        const val androidXCoreTesting = "androidx.test:core:$androidXCoreTestingVersion"
        const val robolectric = "org.robolectric:robolectric:$robolectricVersion"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitConverterMoshi =
            "com.squareup.retrofit2:converter-moshi:$retrofitConverterMoshiVersion"
        const val moshi = "com.squareup.moshi:moshi-kotlin:$moshiVersion"

        const val okHttp3 = "com.squareup.okhttp3:okhttp:$okHttp3Version"
        const val okHttp3LoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:$okHttp3LoggingInterceptorVersion"

        const val dagger2 = "com.google.dagger:dagger:$dagger2Version"
        const val dagger2compiler = "com.google.dagger:dagger-compiler:$dagger2Version"

        const val multidex = "androidx.multidex:multidex:$multidexVersion"

        const val navigationFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:$navigationVersion"

        const val activityKtx = "androidx.activity:activity-ktx:$activityKtxVersion"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:$fragmentKtxVersion"

        const val adapterDelegates =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:$adapterDelegatesVersion"

        const val room = "androidx.room:room-runtime:$roomVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
        const val roomKtxCompiler = "androidx.room:room-ktx:$roomVersion"

        const val scarlet = "com.tinder.scarlet:scarlet:$scarletVersion"
        const val scarletCore = "com.tinder.scarlet:scarlet-core:$scarletVersion"
        const val scarletWebsocketOkhttp = "com.tinder.scarlet:websocket-okhttp:$scarletVersion"
        const val scarletLifecycleAndroid = "com.tinder.scarlet:lifecycle-android:$scarletVersion"
        const val scarletMessageAdapterMoshi =
            "com.tinder.scarlet:message-adapter-moshi:$scarletVersion"
        const val scarletStreamAdapterCoroutines =
            "com.tinder.scarlet:stream-adapter-coroutines:$scarletVersion"

        const val facebookShimmer = "com.facebook.shimmer:shimmer:$facebookShimmerVersion"
        const val picasso = "com.squareup.picasso:picasso:$picassoVersion"
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
