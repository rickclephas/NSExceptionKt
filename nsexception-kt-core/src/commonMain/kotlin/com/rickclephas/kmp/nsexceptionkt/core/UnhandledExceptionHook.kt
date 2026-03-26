package com.rickclephas.kmp.nsexceptionkt.core

import kotlin.experimental.ExperimentalNativeApi
import kotlin.concurrent.AtomicReference
import kotlin.concurrent.AtomicInt

/**
 * Wraps the unhandled exception hook such that the provided [hook] is invoked
 * before the currently set unhandled exception hook is invoked.
 * Note: once the unhandled exception hook returns the program will be terminated.
 * @see setUnhandledExceptionHook
 * @see terminateWithUnhandledException
 */
@OptIn(ExperimentalNativeApi::class)
internal fun wrapUnhandledExceptionHook(hook: (Throwable) -> Unit) {
    val prevHook = AtomicReference<ReportUnhandledExceptionHook?>(null)
    val isReporting = AtomicInt(0)
    val wrappedHook: ReportUnhandledExceptionHook = {
        if (isReporting.compareAndSet(0, 1)) {
            try {
                hook(it)
            } catch (e: Throwable) {}
            prevHook.value?.invoke(it)
        }
        terminateWithUnhandledException(it)
    }
    prevHook.value = setUnhandledExceptionHook(wrappedHook)
}
