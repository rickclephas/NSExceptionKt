package com.rickclephas.kmp.nsexceptionkt.core

import kotlin.experimental.ExperimentalNativeApi
import kotlin.concurrent.AtomicReference

/**
 * Wraps the unhandled exception hook such that the provided [hook] is invoked
 * before the currently set unhandled exception hook is invoked.
 * Note: once the unhandled exception hook returns the program will be terminated.
 * @see setUnhandledExceptionHook
 * @see terminateWithUnhandledException
 */
@InternalNSExceptionKtApi
@OptIn(ExperimentalNativeApi::class)
public fun wrapUnhandledExceptionHook(hook: (Throwable) -> Unit) {
    val prevHook = AtomicReference<ReportUnhandledExceptionHook?>(null)
    val wrappedHook: ReportUnhandledExceptionHook = {
        hook(it)
        prevHook.value?.invoke(it)
        terminateWithUnhandledException(it)
    }
    prevHook.value = setUnhandledExceptionHook(wrappedHook)
}
