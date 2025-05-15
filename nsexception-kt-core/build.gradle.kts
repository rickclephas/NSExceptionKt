plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.multiplatform)
    `nsexception-kt-publish`
}

kotlin {
    explicitApi()
    jvmToolchain(11)

    listOf(
        macosX64(), macosArm64(),
        iosArm64(), iosX64(), iosSimulatorArm64(),
        watchosArm32(), watchosArm64(), watchosX64(), watchosSimulatorArm64(), watchosDeviceArm64(),
        tvosArm64(), tvosX64(), tvosSimulatorArm64()
    ).forEach {
        it.compilations.getByName("main") {
            cinterops.create("NSExceptionKtCoreObjC") {
                includeDirs("$projectDir/../NSExceptionKtCoreObjC")
            }
        }
    }

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
