// swift-tools-version: 5.5
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "NSExceptionKt",
    platforms: [.iOS(.v15), .macOS(.v10_15), .tvOS(.v15), .watchOS(.v7)],
    products: [
        .library(
            name: "NSExceptionKtCrashlytics",
            targets: ["NSExceptionKtCrashlytics"]
        ),
        .library(
            name: "NSExceptionKtBugsnag",
            targets: ["NSExceptionKtBugsnag"]
        )
    ],
    dependencies: [
        .package(
            url: "https://github.com/firebase/firebase-ios-sdk.git",
            "9.3.0"..<"13.0.0"
        ),
        .package(
            url: "https://github.com/bugsnag/bugsnag-cocoa.git",
            "6.22.1"..<"7.0.0"
        )
    ],
    targets: [
        .target(
            name: "NSExceptionKtCoreObjC",
            path: "NSExceptionKtCoreObjC",
            publicHeadersPath: "."
        ),
        
        .target(
            name: "NSExceptionKtCrashlyticsObjC",
            path: "NSExceptionKtCrashlyticsObjC",
            publicHeadersPath: "."
        ),
        .target(
            name: "NSExceptionKtCrashlytics",
            dependencies: [
                .target(name: "NSExceptionKtCoreObjC"),
                .target(name: "NSExceptionKtCrashlyticsObjC"),
                .product(name: "FirebaseCrashlytics", package: "firebase-ios-sdk")
            ],
            path: "NSExceptionKtCrashlytics"
        ),
        
        .target(
            name: "NSExceptionKtBugsnag",
            dependencies: [
                .target(name: "NSExceptionKtCoreObjC"),
                .product(name: "Bugsnag", package: "bugsnag-cocoa")
            ],
            path: "NSExceptionKtBugsnag"
        )
    ],
    swiftLanguageVersions: [.v5]
)
