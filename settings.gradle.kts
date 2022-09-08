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
include(":modules:core:core_factory")
include(":modules:core:core_network_api")
include(":modules:core:core_network_impl")
include(":modules:core:core_di_api")
include(":modules:core:core_di_impl")
include(":modules:feature:feature_app_activity_api")
include(":modules:feature:feature_app_activity_impl")
include(":modules:feature:feature_app_flow_fragment_api")
include(":modules:feature:feature_app_flow_fragment_impl")
include(":modules:feature:feature_coins_list_fragment_api")
include(":modules:feature:feature_coins_list_fragment_impl")
include(":modules:feature:feature_favorite_coins_fragment_api")
include(":modules:feature:feature_favorite_coins_fragment_impl")
include(":modules:core:core_api")
