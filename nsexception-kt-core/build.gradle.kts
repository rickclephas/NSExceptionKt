plugins {
    id("nsexception-kt-kotlin-multiplatform")
    id("nsexception-kt-publish")
}

kotlin {
    explicitApi()
    jvmToolchain(11)

    listOf(
        macosArm64(),
        iosArm64(), iosX64(), iosSimulatorArm64(),
        watchosArm64(), watchosSimulatorArm64(), watchosDeviceArm64(),
        tvosArm64(), tvosSimulatorArm64()
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
