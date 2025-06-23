plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.vanniktech.mavenPublish)
}
