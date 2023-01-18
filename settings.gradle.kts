@file:Suppress("UnstableApiUsage")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven { url = uri("https://jitpack.io") }
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
rootProject.name = "Okkv2"
include(":okkv2-core")
include(":okkv2-mmkv")
include(":okkv2-sp")
include(":okkv2-compose")
