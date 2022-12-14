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
    api(project(Dependencies.ProjectModules.coreDbApi))
    implementation(project(Dependencies.ProjectModules.coreDiApi))

    implementation(Dependencies.Libraries.room)
    implementation(Dependencies.Libraries.roomKtxCompiler)
    kapt(Dependencies.Libraries.roomCompiler)

    implementation(Dependencies.Libraries.dagger2)
    kapt(Dependencies.Libraries.dagger2compiler)

    implementation(Dependencies.Libraries.kotlinxCoroutines)

    testImplementation(Dependencies.Libraries.junit)
    androidTestImplementation(Dependencies.Libraries.turbine)
    androidTestImplementation(Dependencies.Libraries.androidxJunit)
    androidTestImplementation(Dependencies.Libraries.truth)
    androidTestImplementation(Dependencies.Libraries.androidArchCoreCoreTesting)
    androidTestImplementation(Dependencies.Libraries.coroutinesTesting)
    androidTestImplementation(Dependencies.Libraries.androidxEspresso)
}