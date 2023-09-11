plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.multiplatform)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings {
                compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        iosMain {
            dependencies {
                implementation("com.rickclephas.kmp:nsexception-kt-bugsnag")
                implementation("com.rickclephas.kmp:nsexception-kt-crashlytics")
                implementation("com.rickclephas.kmp:nsexception-kt-sentry")
            }
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 27
        targetSdk = 32
    }
}
