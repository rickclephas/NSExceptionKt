package com.rickclephas.kmp.nsexceptionkt.crashlytics

import FirebaseCrashlytics.FIRCLSExceptionRecordModel
import FirebaseCrashlytics.FIRCLSExceptionRecordNSException
import FirebaseCrashlytics.FIRExceptionModel
import FirebaseCrashlytics.FIRStackFrame
import com.rickclephas.kmp.nsexceptionkt.core.asNSException
import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.NSException
import platform.Foundation.NSNumber
import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

public enum class CausedByStrategy {
    IGNORE, APPEND, LOG_NON_FATAL
}

@OptIn(ExperimentalStdlibApi::class)
public fun setCrashlyticsUnhandledExceptionHook(causedByStrategy: CausedByStrategy = CausedByStrategy.IGNORE) {
    val prevHook = AtomicReference<ReportUnhandledExceptionHook?>(null)
    val hook: ReportUnhandledExceptionHook = {
        if (causedByStrategy == CausedByStrategy.LOG_NON_FATAL) {
            recursivelyLogCause(it)
        }
        val exception = it.asNSException(causedByStrategy == CausedByStrategy.APPEND)
        FIRCLSExceptionRecordNSException(exception)
        prevHook.value?.invoke(it)
        terminateWithUnhandledException(it)
    }
    prevHook.value = setUnhandledExceptionHook(hook.freeze())
}

@OptIn(UnsafeNumber::class)
private fun NSException.asFIRExceptionModel(): FIRExceptionModel = FIRExceptionModel(
    name, reason ?: ""
).apply {
    stackTrace = callStackReturnAddresses.map {
        FIRStackFrame.stackFrameWithAddress((it as NSNumber).unsignedIntegerValue)
    }
}

private fun recursivelyLogCause(throwable: Throwable) {
    val cause = throwable.cause?.also(::recursivelyLogCause) ?: return
    val exceptionModel = cause.asNSException().asFIRExceptionModel()
    FIRCLSExceptionRecordModel(exceptionModel)
}
