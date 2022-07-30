pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "NSExceptionKtSample"

include(":shared")

includeBuild("..") {
    dependencySubstitution {
        listOf("bugsnag", "core", "crashlytics", "sentry").forEach {
            substitute(module("com.rickclephas.kmp:nsexception-kt-$it"))
                .using(project(":nsexception-kt-$it"))
        }
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
