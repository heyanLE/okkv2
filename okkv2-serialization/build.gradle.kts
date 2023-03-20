@file:Suppress("UnstableApiUsage")
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "loli.ball.okkv2.serialization"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

afterEvaluate {
    publishing {
        publications {
            create("maven_public", MavenPublication::class) {
                groupId = "com.heyanle"
                artifactId = "okkv2-serialization"
                version = "1.2"
                from(components.getByName("release"))
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    api(project(":okkv2-core"))
}