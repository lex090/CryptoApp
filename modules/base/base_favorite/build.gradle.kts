import com.github.lex090.cryptoapp.dependencies.AppConfiguration
import com.github.lex090.cryptoapp.dependencies.Dependencies

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
    implementation(project(Dependencies.ProjectModules.coreDbApi))
    implementation(project(Dependencies.ProjectModules.coreApi))
    implementation(project(Dependencies.ProjectModules.baseCoins))

    implementation(Dependencies.Libraries.kotlinxCoroutines)

    implementation(Dependencies.Libraries.dagger2)
    kapt(Dependencies.Libraries.dagger2compiler)

    testImplementation(Dependencies.Libraries.junit)
    testImplementation(Dependencies.Libraries.mockitoCore)
    testImplementation(Dependencies.Libraries.mockitoKotlin)
    testImplementation(Dependencies.Libraries.coroutinesTesting)
}