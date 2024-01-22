//
//  BugsnagError+NSException.swift
//  NSExceptionKtBugsnag
//
//  Created by Rick Clephas on 21/01/2024.
//

import Bugsnag

internal extension BugsnagError {
    /// Creates a BugsnagError from a NSException.
    convenience init(_ exception: NSException) {
        self.init()
        errorClass = exception.name.rawValue
        errorMessage = exception.reason
        stacktrace = BugsnagStackframe.stackframes(withCallStackReturnAddresses: exception.callStackReturnAddresses)
        type = BSGErrorType.cocoa
    }
}
