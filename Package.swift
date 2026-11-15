// swift-tools-version: 5.7
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "NSExceptionKt",
    platforms: [.iOS(.v15), .macOS(.v12), .tvOS(.v15), .watchOS(.v9)],
    products: [
        .library(
            name: "NSExceptionKtCrashlytics",
            targets: ["NSExceptionKtCrashlytics"]
        )
    ],
    dependencies: [
        .package(
            url: "https://github.com/firebase/firebase-ios-sdk.git",
            "9.3.0"..<"14.0.0"
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
        )
    ],
    swiftLanguageVersions: [.v5]
)
