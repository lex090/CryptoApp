import com.github.lex090.cryptoapp.dependencies.AppConfiguration
import com.github.lex090.cryptoapp.dependencies.Dependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(project(Dependencies.ProjectModules.featureFullCoinInfoApi))
    implementation(project(Dependencies.ProjectModules.baseCoins))
    implementation(project(Dependencies.ProjectModules.coreApi))
    implementation(project(Dependencies.ProjectModules.coreDiApi))

    implementation(Dependencies.Libraries.androidxCoreKtx)
    implementation(Dependencies.Libraries.androidxAppcompat)
    implementation(Dependencies.Libraries.material)
    implementation(Dependencies.Libraries.androidxConstraintLayout)

    implementation(Dependencies.Libraries.navigationFragmentKtx)
    implementation(Dependencies.Libraries.navigationUiKtx)

    implementation(Dependencies.Libraries.dagger2)
    kapt(Dependencies.Libraries.dagger2compiler)

}