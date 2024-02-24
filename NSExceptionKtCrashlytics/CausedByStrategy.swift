//
//  CausedByStrategy.swift
//  NSExceptionKtCrashlytics
//
//  Created by Rick Clephas on 20/01/2024.
//

/// Defines strategies for logging Throwable causes.
public enum CausedByStrategy {
    /// Causes will be ignored, only the main Throwable is logged as a fatal exception.
    case ignore
    /// Causes are appended to the main Throwable and logged as a single fatal exception.
    case append
    /// All causes are logged as non-fatal exceptions before the main Throwable is logged as a fatal exception.
    case logNonFatal
}
