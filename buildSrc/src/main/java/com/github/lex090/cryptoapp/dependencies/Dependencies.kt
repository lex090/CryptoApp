package com.github.lex090.cryptoapp.dependencies

import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxAppcompatVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxConstraintLayoutVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxCoreKtxVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxEspressoVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.androidxJunitVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.junitVersion
import com.github.lex090.cryptoapp.dependencies.Dependencies.Versions.materialVersion

object Dependencies {

    private object Versions {
        const val androidxCoreKtxVersion = "1.7.0"
        const val androidxAppcompatVersion = "1.5.0"
        const val materialVersion = "1.6.1"
        const val androidxConstraintLayoutVersion = "1.6.1"
        const val junitVersion = "4.13.2"
        const val androidxJunitVersion = "1.1.3"
        const val androidxEspressoVersion = "3.4.0"
    }

    object ProjectModules {

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

