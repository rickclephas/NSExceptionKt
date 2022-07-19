package com.rickclephas.kmp.nsexceptionkt.crashlytics

import FirebaseCrashlytics.FIRCLSExceptionRecordModel
import FirebaseCrashlytics.FIRCLSExceptionRecordNSException
import FirebaseCrashlytics.FIRExceptionModel
import FirebaseCrashlytics.FIRStackFrame
import com.rickclephas.kmp.nsexceptionkt.core.asNSException
import com.rickclephas.kmp.nsexceptionkt.core.causes
import com.rickclephas.kmp.nsexceptionkt.core.wrapUnhandledExceptionHook
import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.NSException
import platform.Foundation.NSNumber

/**
 * Defines strategies for logging [causes][Throwable.cause].
 */
public enum class CausedByStrategy {
    /**
     * Causes will be ignored,
     * only the main [Throwable] is logged as a fatal exception.
     */
    IGNORE,
    /**
     * Causes are appended to the main [Throwable]
     * and logged as a single fatal exception.
     */
    APPEND,
    /**
     * All causes are logged as non-fatal exceptions
     * before the main [Throwable] is logged as a fatal exception.
     */
    LOG_NON_FATAL
}

/**
 * Sets the unhandled exception hook such that all unhandled exceptions are logged to Crashlytics as fatal exceptions.
 * If an unhandled exception hook was already set, that hook will be invoked after the exception is logged.
 * Note: once the exception is logged the program will be terminated.
 * @param causedByStrategy the strategy used to log [causes][Throwable.cause].
 * @see wrapUnhandledExceptionHook
 */
public fun setCrashlyticsUnhandledExceptionHook(
    causedByStrategy: CausedByStrategy = CausedByStrategy.IGNORE
): Unit = wrapUnhandledExceptionHook { throwable ->
    if (causedByStrategy == CausedByStrategy.LOG_NON_FATAL) {
        throwable.causes.asReversed().forEach { cause ->
            FIRCLSExceptionRecordModel(cause.asNSException().asFIRExceptionModel())
        }
    }
    val exception = throwable.asNSException(causedByStrategy == CausedByStrategy.APPEND)
    FIRCLSExceptionRecordNSException(exception)
}

/**
 * Converts `this` [NSException] to a [FIRExceptionModel].
 * An empty string is used as reason in case [reason][NSException.reason] is `null`.
 */
@OptIn(UnsafeNumber::class)
private fun NSException.asFIRExceptionModel(): FIRExceptionModel = FIRExceptionModel(
    name, reason ?: ""
).apply {
    stackTrace = callStackReturnAddresses.map {
        FIRStackFrame.stackFrameWithAddress((it as NSNumber).unsignedIntegerValue)
    }
}
