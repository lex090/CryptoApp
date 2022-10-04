plugins {
    id("com.android.application").version("7.2.2").apply(false)
    id("com.android.library").version("7.2.2").apply(false)
    id("org.jetbrains.kotlin.android").version("1.7.10").apply(false)
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        val navVersion = "2.5.2"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}