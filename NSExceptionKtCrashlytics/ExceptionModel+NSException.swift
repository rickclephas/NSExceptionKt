//
//  ExceptionModel+NSException.swift
//  NSExceptionKtCrashlytics
//
//  Created by Rick Clephas on 20/01/2024.
//

import FirebaseCrashlytics

internal extension ExceptionModel {
    convenience init(_ exception: NSException) {
        self.init(name: exception.name.rawValue, reason: exception.reason ?? "")
        stackTrace = exception.callStackReturnAddresses.map { address in
            StackFrame(address: address.uintValue)
        }
    }
}
