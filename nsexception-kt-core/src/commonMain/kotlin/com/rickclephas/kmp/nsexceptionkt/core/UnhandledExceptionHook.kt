package com.rickclephas.kmp.nsexceptionkt.core

import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

/**
 * Wraps the unhandled exception hook such that the provided [hook] is invoked
 * before the currently set unhandled exception hook is invoked.
 * Note: once the unhandled exception hook returns the program will be terminated.
 * @see setUnhandledExceptionHook
 * @see terminateWithUnhandledException
 */
@OptIn(ExperimentalStdlibApi::class)
public fun wrapUnhandledExceptionHook(hook: (Throwable) -> Unit) {
    val prevHook = AtomicReference<ReportUnhandledExceptionHook?>(null)
    val wrappedHook: ReportUnhandledExceptionHook = {
        hook(it)
        prevHook.value?.invoke(it)
        terminateWithUnhandledException(it)
    }
    prevHook.value = setUnhandledExceptionHook(wrappedHook.freeze())
}
