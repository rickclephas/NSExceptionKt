// swift-tools-version: 5.7
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "NSExceptionKt",
    platforms: [.iOS(.v15), .macOS(.v12), .tvOS(.v15), .watchOS(.v9)],
    products: [
        .library(
            name: "NSExceptionKtBugsnag",
            targets: ["NSExceptionKtBugsnag"]
        )
    ],
    dependencies: [
        .package(
            url: "https://github.com/bugsnag/bugsnag-cocoa.git",
            "6.22.1"..<"8.0.0"
        )
    ],
    targets: [
        .target(
            name: "NSExceptionKtCoreObjC",
            path: "NSExceptionKtCoreObjC",
            publicHeadersPath: "."
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
