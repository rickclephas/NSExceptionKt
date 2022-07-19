package com.rickclephas.kmp.nsexceptionkt.sentry

import Sentry.*
import com.rickclephas.kmp.nsexceptionkt.core.asNSException
import com.rickclephas.kmp.nsexceptionkt.core.wrapUnhandledExceptionHook
import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.NSException
import platform.Foundation.NSNumber

public fun setSentryUnhandledExceptionHook(): Unit = wrapUnhandledExceptionHook { throwable ->
    val event = throwable.asSentryEvent()
    SentrySDK.captureCrashEvent(event)
    // TODO: Wait before crashing
}

@Suppress("UnnecessaryOptInAnnotation")
@OptIn(UnsafeNumber::class)
private fun Throwable.asSentryEvent(): SentryEvent = SentryEvent(kSentryLevelFatal).apply {
    @Suppress("UNCHECKED_CAST")
    val threads = threadInspector?.getCurrentThreadsWithStackTrace() as List<SentryThread>?
    this.threads = threads
    val currentThread = threads?.firstOrNull { it.current?.boolValue ?: false }?.apply {
        NSExceptionKt_SentryThreadSetCrashed(this)
        // Crashed threats shouldn't have a stacktrace, the trait_id should be set on the exception instead
        // https://develop.sentry.dev/sdk/event-payloads/threads/
        stacktrace = null
    }
    exceptions = buildList {
        var throwable: Throwable? = this@asSentryEvent
        while (throwable != null) {
            add(0, throwable.asNSException(false).asSentryException(currentThread?.threadId))
            throwable = throwable.cause
        }
    }
}

private fun NSException.asSentryException(
    threadId: NSNumber?
): SentryException = SentryException(reason ?: "", name).apply {
    this.threadId = threadId
    mechanism = SentryMechanism("generic").apply {
        NSExceptionKt_SentryMechanismSetNotHandled(this)
    }
    stacktrace = threadInspector?.stacktraceBuilder?.let { stacktraceBuilder ->
        val cursor = NSExceptionKt_SentryCrashStackCursorFromNSException(this@asSentryException)
        stacktraceBuilder.retrieveStacktraceFromCursor(cursor)
    }
}

private val threadInspector: SentryThreadInspector?
    get() = SentrySDK.currentHub?.getClient()?.threadInspector
