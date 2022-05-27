group = "com.heyanle"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath ("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
        classpath ("com.android.tools.build:gradle:7.0.4")
    }
}


