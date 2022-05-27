plugins {
    id ("com.android.library")
    id ("kotlin-android")
    id("maven-publish")
}

android {
    compileSdk = 32
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),  "proguard-rules.pro")
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

publishing {
    publications {
        create("maven_public", MavenPublication::class) {
            groupId = "com.heyanle"
            artifactId = "okkv2-core"
            version = "1.1"
            from(components.getByName("release"))
        }
    }
}


dependencies {
    api(project(":okkv2-core"))
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}