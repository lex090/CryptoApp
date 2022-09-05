import com.github.lex090.cryptoapp.dependencies.AppConfiguration
import com.github.lex090.cryptoapp.dependencies.Dependencies
import com.github.lex090.cryptoapp.dependencies.Dependencies.ProjectModules

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
    api(project(ProjectModules.coreNetworkApi))
    api(project(ProjectModules.coreDiApi))

    implementation(Dependencies.Libraries.retrofit)
    implementation(Dependencies.Libraries.retrofitConverterMoshi)

    implementation(Dependencies.Libraries.okHttp3)
    implementation(Dependencies.Libraries.okHttp3LoggingInterceptor)

    implementation(Dependencies.Libraries.dagger2)
    kapt(Dependencies.Libraries.dagger2compiler)
}