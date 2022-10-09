plugins {
    id("com.android.application").version("7.2.2").apply(false)
    id("com.android.library").version("7.2.2").apply(false)
    id("org.jetbrains.kotlin.android").version("1.7.10").apply(false)
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
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

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.21.0")
}

allprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        toolVersion = "1.21.0"
        parallel = true
        baseline = file("${rootDir}/config/detekt/baseline.xml")
        config = files("${rootDir}/config/detekt/detekt.yml")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

val detektProjectBaseline by tasks.registering(io.gitlab.arturbosch.detekt.DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    parallel.set(true)
    setSource(files(rootDir))
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline.set(file("$rootDir/config/detekt/baseline.xml"))
}


tasks.register("installGitHook", Copy::class) {
    from(file("$rootDir/githooks/pre-commit"))
    into(file("$rootDir/.git/hooks"))
    fileMode = 777
}
tasks.getByPath(":app:preBuild").dependsOn(":installGitHook")