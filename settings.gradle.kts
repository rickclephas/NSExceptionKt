pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "NSExceptionKt"

include(":nsexception-kt-bugsnag")
include(":nsexception-kt-core")
include(":nsexception-kt-crashlytics")
