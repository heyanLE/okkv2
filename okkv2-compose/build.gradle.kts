@file:Suppress("UnstableApiUsage")
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "loli.ball.okkv2.compose"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

afterEvaluate {
    publishing {
        publications {
            create("maven_public", MavenPublication::class) {
                groupId = "com.heyanle"
                artifactId = "okkv2-compose"
                version = "1.2"
                from(components.getByName("release"))
            }
        }
    }
}

dependencies {
    implementation("androidx.compose.runtime:runtime:1.3.3")
    api(project(":okkv2-core"))
}