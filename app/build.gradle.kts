import com.github.lex090.cryptoapp.dependencies.AppConfiguration
import com.github.lex090.cryptoapp.dependencies.Dependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = AppConfiguration.compileSdk

    defaultConfig {
        applicationId = AppConfiguration.applicationId
        minSdk = AppConfiguration.minSdk
        targetSdk = AppConfiguration.targetSdk
        versionCode = AppConfiguration.versionCode
        versionName = AppConfiguration.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    implementation(project(Dependencies.ProjectModules.coreFactory))

    implementation(project(Dependencies.ProjectModules.coreDiImpl))

    implementation(project(Dependencies.ProjectModules.coreNetworkImpl))

    implementation(project(Dependencies.ProjectModules.coreApi))

    implementation(project(Dependencies.ProjectModules.baseUI))

    implementation(project(Dependencies.ProjectModules.coreDbImpl))

    implementation(project(Dependencies.ProjectModules.baseFavorite))

    implementation(project(Dependencies.ProjectModules.baseCoins))

    implementation(project(Dependencies.ProjectModules.featureAppActivityImpl))

    implementation(project(Dependencies.ProjectModules.featureAppFlowFragmentImpl))

    implementation(project(Dependencies.ProjectModules.featureCoinsListFragmentImpl))

    implementation(project(Dependencies.ProjectModules.featureFavoriteCoinsFragmentImpl))

    implementation(project(Dependencies.ProjectModules.featureFullCoinInfoImpl))

    implementation(Dependencies.Libraries.androidxCoreKtx)
    implementation(Dependencies.Libraries.androidxAppcompat)
    implementation(Dependencies.Libraries.material)
    implementation(Dependencies.Libraries.androidxConstraintLayout)

    testImplementation(Dependencies.Libraries.junit)
    androidTestImplementation(Dependencies.Libraries.androidxJunit)
    androidTestImplementation(Dependencies.Libraries.androidxEspresso)

    implementation(Dependencies.Libraries.okHttp3)

    implementation(Dependencies.Libraries.multidex)

    implementation(Dependencies.Libraries.dagger2)
    kapt(Dependencies.Libraries.dagger2compiler)
}