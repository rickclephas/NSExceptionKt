//
//  CrashlyticsNSExceptionKtReporter.swift
//  NSExceptionKtCrashlytics
//
//  Created by Rick Clephas on 20/01/2024.
//

import NSExceptionKtCoreObjC
import NSExceptionKtCrashlyticsObjC
import FirebaseCrashlytics

public extension NSExceptionKtReporter where Self == NSExceptionKtReporter {
    static func crashlytics(_ crashlytics: Crashlytics = Crashlytics.crashlytics(), causedByStrategy: CausedByStrategy) -> NSExceptionKtReporter {
        CrashlyticsNSExceptionKtReporter(crashlytics, causedByStrategy: causedByStrategy)
    }
}

private class CrashlyticsNSExceptionKtReporter: NSExceptionKtReporter {
    
    private let crashlytics: Crashlytics
    private let causedByStrategy: CausedByStrategy
    
    public var requiresMergedException: Bool { causedByStrategy == .append }
    
    init(_ crashlytics: Crashlytics, causedByStrategy: CausedByStrategy) {
        self.crashlytics = crashlytics
        self.causedByStrategy = causedByStrategy
    }
    
    public func reportException(_ exceptions: [NSException]) {
        if causedByStrategy == .logNonFatal {
            for exception in exceptions.reversed().dropLast() {
                crashlytics.record(exceptionModel: .init(exception))
            }
        }
        guard let exception = exceptions.first else { return }
        FIRCLSExceptionRecordNSException(exception)
    }
}
