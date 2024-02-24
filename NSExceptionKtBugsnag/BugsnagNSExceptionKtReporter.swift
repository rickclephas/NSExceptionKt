//
//  BugsnagNSExceptionKtReporter.swift
//  NSExceptionKtBugsnag
//
//  Created by Rick Clephas on 21/01/2024.
//

import NSExceptionKtCoreObjC
import Bugsnag

public extension NSExceptionKtReporter where Self == NSExceptionKtReporter {
    /// Configures Bugsnag and creates a ``NSExceptionKtCoreObjC/NSExceptionKtReporter`` that will report unhandled Kotlin exceptions to Bugsnag.
    /// - Parameter config: The Bugsnag configuration that will be updated to support unhandled Kotlin exceptions.
    /// - Returns: A ``NSExceptionKtCoreObjC/NSExceptionKtReporter`` that will report exceptions to Bugsnag.
    static func bugsnag(_ config: BugsnagConfiguration) -> NSExceptionKtReporter {
        overrideOriginalUnhandledValue()
        config.addOnSendError { event in
            !event.unhandled || !event.featureFlags.contains { $0.name == kotlinCrashedFeatureFlag }
        }
        config.clearFeatureFlag(name: kotlinCrashedFeatureFlag)
        return BugsnagNSExceptionKtReporter.shared
    }
}

private func overrideOriginalUnhandledValue() {
    // In Bugsnag 6.26.2 and above we need to override the originalUnhandledValue property.
    // By default it will prevent our exceptions from being stored to disk.
    // https://github.com/bugsnag/bugsnag-cocoa/pull/1549
    let handledStateClass: AnyClass = NSClassFromString("BugsnagHandledState")!
    guard let originalMethod = class_getInstanceMethod(handledStateClass, NSSelectorFromString("originalUnhandledValue")) else { return }
    let method = class_getInstanceMethod(handledStateClass, NSSelectorFromString("unhandled"))!
    method_setImplementation(originalMethod, method_getImplementation(method))
}

private let kotlinCrashedFeatureFlag = "nsexceptionkt.kotlin_crashed"

private class BugsnagNSExceptionKtReporter: NSExceptionKtReporter {
    
    static let shared = BugsnagNSExceptionKtReporter()
    
    private init() {}
    
    var requiresMergedException: Bool = false
    
    func reportException(_ exceptions: [NSException]) {
        guard let exception = exceptions.first else { return }
        Bugsnag.notify(exception) { event in
            event.unhandled = true
            event.severity = BSGSeverity.error
            event.errors += exceptions.dropFirst().map(BugsnagError.init)
            return true
        }
        Bugsnag.addFeatureFlag(name: kotlinCrashedFeatureFlag)
    }
}
