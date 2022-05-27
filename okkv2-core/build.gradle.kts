import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("maven-publish")
}

group = "com.heyanle"
version = "1.0-SNAPSHOT"


dependencies {

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


publishing {
    publications {
        create("maven_public", MavenPublication::class) {
            groupId = "com.heyanle"
            artifactId = "okkv2-core"
            version = "1.1"
            from(components.getByName("java"))
        }
    }
}