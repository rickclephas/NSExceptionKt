package com.rickclephas.kmp.nsexceptionkt.bugsnag

import Bugsnag.*
import com.rickclephas.kmp.nsexceptionkt.core.asNSException
import com.rickclephas.kmp.nsexceptionkt.core.causes
import com.rickclephas.kmp.nsexceptionkt.core.wrapUnhandledExceptionHook
import platform.Foundation.NSException

/**
 * Configures Bugsnag to ignore the Kotlin termination crash.
 */
public fun configureBugsnag(config: BugsnagConfiguration) {
    NSExceptionKt_BugsnagConfigAddOnSendErrorBlock(config) { event ->
        if (event == null) return@NSExceptionKt_BugsnagConfigAddOnSendErrorBlock true
        !event.unhandled || event.featureFlags.none { (it as BugsnagFeatureFlag).name == kotlinCrashedFeatureFlag }
    }
    config.clearFeatureFlagWithName(kotlinCrashedFeatureFlag)
}

/**
 * Sets the unhandled exception hook such that all unhandled exceptions are logged to Bugsnag as fatal exceptions.
 * If an unhandled exception hook was already set, that hook will be invoked after the exception is logged.
 * Note: once the exception is logged the program will be terminated.
 * @see wrapUnhandledExceptionHook
 */
public fun setBugsnagUnhandledExceptionHook(): Unit = wrapUnhandledExceptionHook { throwable ->
    val exception = throwable.asNSException()
    val causes = throwable.causes.map { it.asNSException() }
    Bugsnag.notify(exception) { event ->
        if (event == null) return@notify true
        event.unhandled = true
        event.severity = BSGSeverity.BSGSeverityError
        if (causes.isNotEmpty()) {
            event.errors += causes.map { it.asBugsnagError() }
        }
        true
    }
    Bugsnag.addFeatureFlagWithName(kotlinCrashedFeatureFlag)
}

/**
 * Feature flag used to mark the Kotlin termination crash.
 */
private const val kotlinCrashedFeatureFlag = "com.rickclephas.kmp.nsexceptionkt.bugsnag.kotlin_crashed"

/**
 * Converts `this` [NSException] to a [BugsnagError].
 */
private fun NSException.asBugsnagError(): BugsnagError = BugsnagError().apply {
    errorClass = name
    errorMessage = reason
    stacktrace = BugsnagStackframe.stackframesWithCallStackReturnAddresses(callStackReturnAddresses)!!
    type = BSGErrorType.BSGErrorTypeCocoa
}
