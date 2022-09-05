pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "CryptoApp"
include(":app")
include(":modules:core:core_network_api")
include(":modules:core:core_network_impl")
include(":modules:core:core_di_api")
include(":modules:core:core_di_impl")
include(":modules:core:core_factory")
