package com.rickclephas.kmp.nsexceptionkt.core

import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.convert
import platform.Foundation.NSException
import platform.Foundation.NSNumber
import platform.darwin.NSUInteger

@OptIn(UnsafeNumber::class)
public fun Throwable.asNSException(appendCausedBy: Boolean = false): NSException {
    val returnAddresses = getFilteredStackTraceAddresses().let { addresses ->
        if (!appendCausedBy) return@let addresses
        addresses.toMutableList().apply {
            var cause = cause
            while (cause != null) {
                addAll(cause.getFilteredStackTraceAddresses(true, addresses))
                cause = cause.cause
            }
        }
    }.map {
        @Suppress("RemoveExplicitTypeArguments")
        NSNumber(unsignedInteger = it.convert<NSUInteger>())
    }
    return ThrowableNSException(name, getReason(appendCausedBy), returnAddresses)
}

internal val Throwable.name: String
    get() = this::class.qualifiedName ?: this::class.simpleName ?: "Throwable"

internal fun Throwable.getReason(appendCausedBy: Boolean = false): String? {
    if (!appendCausedBy) return message
    return buildString {
        message?.let(::append)
        var cause = cause
        while (cause != null) {
            if (isNotEmpty()) append(" ")
            append("Caused by: ")
            append(cause.name)
            cause.message?.let { append(": $it") }
            cause = cause.cause
        }
    }
}

internal class ThrowableNSException(
    name: String,
    reason: String?,
    private val returnAddresses: List<NSNumber>
): NSException(name, reason, null) {
    override fun callStackReturnAddresses(): List<NSNumber> = returnAddresses
}
