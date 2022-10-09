import com.github.lex090.cryptoapp.dependencies.AppConfiguration
import com.github.lex090.cryptoapp.dependencies.Dependencies.Libraries

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = AppConfiguration.compileSdk

    defaultConfig {
        minSdk = AppConfiguration.minSdk
        targetSdk = AppConfiguration.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Libraries.okHttp3)

    implementation(Libraries.scarlet)
    implementation(Libraries.scarletCore)
    implementation(Libraries.scarletWebsocketOkhttp)
    implementation(Libraries.scarletLifecycleAndroid)
    implementation(Libraries.scarletMessageAdapterMoshi)
    implementation(Libraries.scarletStreamAdapterCoroutines)

    implementation(Libraries.dagger2)
    kapt(Libraries.dagger2compiler)
}