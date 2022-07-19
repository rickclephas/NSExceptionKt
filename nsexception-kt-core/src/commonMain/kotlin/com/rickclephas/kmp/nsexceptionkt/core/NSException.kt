package com.rickclephas.kmp.nsexceptionkt.core

import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.convert
import platform.Foundation.NSException
import platform.Foundation.NSNumber
import platform.darwin.NSUInteger
import kotlin.reflect.KClass

/**
 * Returns a [NSException] representing `this` [Throwable].
 * If [appendCausedBy] is `true` then the name, message and stack trace
 * of the [causes][Throwable.cause] will be appended, else causes are ignored.
 */
@OptIn(UnsafeNumber::class)
public fun Throwable.asNSException(appendCausedBy: Boolean = false): NSException {
    val returnAddresses = getFilteredStackTraceAddresses().let { addresses ->
        if (!appendCausedBy) return@let addresses
        addresses.toMutableList().apply {
            for (cause in causes) {
                addAll(cause.getFilteredStackTraceAddresses(true, addresses))
            }
        }
    }.map {
        @Suppress("RemoveExplicitTypeArguments")
        NSNumber(unsignedInteger = it.convert<NSUInteger>())
    }
    return ThrowableNSException(name, getReason(appendCausedBy), returnAddresses)
}

/**
 * Returns the [qualifiedName][KClass.qualifiedName] or [simpleName][KClass.simpleName] of `this` throwable.
 * If both are `null` then "Throwable" is returned.
 */
internal val Throwable.name: String
    get() = this::class.qualifiedName ?: this::class.simpleName ?: "Throwable"

/**
 * Returns the [message][Throwable.message] of this throwable.
 * If [appendCausedBy] is `true` then caused by lines with the format
 * "Caused by: $[name]: $[message][Throwable.message]" will be appended.
 */
internal fun Throwable.getReason(appendCausedBy: Boolean = false): String? {
    if (!appendCausedBy) return message
    return buildString {
        message?.let(::append)
        for (cause in causes) {
            if (isNotEmpty()) appendLine()
            append("Caused by: ")
            append(cause.name)
            cause.message?.let { append(": $it") }
        }
    }.takeIf { it.isNotEmpty() }
}

internal class ThrowableNSException(
    name: String,
    reason: String?,
    private val returnAddresses: List<NSNumber>
): NSException(name, reason, null) {
    override fun callStackReturnAddresses(): List<NSNumber> = returnAddresses
}
