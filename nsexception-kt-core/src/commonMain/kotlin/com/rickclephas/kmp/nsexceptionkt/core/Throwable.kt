package com.rickclephas.kmp.nsexceptionkt.core

/**
 * Returns a list with all the [causes][Throwable.cause].
 * The first element will be the cause, the second the cause of the cause, etc.
 * This function stops once a reference cycles is detected.
 */
public val Throwable.causes: List<Throwable> get() = buildList {
    val causes = mutableSetOf<Throwable>()
    var cause = cause
    while (cause != null && cause !in causes) {
        add(cause)
        causes.add(cause)
        cause = cause.cause
    }
}

/**
 * Returns a list of stack trace addresses representing
 * the stack trace of the constructor call to `this` [Throwable].
 * @param keepLastInit `true` to preserve the last constructor call, `false` to drop all constructor calls.
 * @param commonAddresses a list of addresses used to drop the last common addresses.
 * @see getStackTraceAddresses
 */
public fun Throwable.getFilteredStackTraceAddresses(
    keepLastInit: Boolean = false,
    commonAddresses: List<Long> = emptyList()
): List<Long> = getStackTraceAddresses().dropInitAddresses(
    qualifiedClassName = this::class.qualifiedName ?: Throwable::class.qualifiedName!!,
    stackTrace = getStackTrace(),
    keepLast = keepLastInit
).dropCommonAddresses(commonAddresses)

/**
 * Returns a list containing all addresses expect for the first addresses
 * matching the constructor call of the [qualifiedClassName].
 * If [keepLast] is `true` the last constructor call won't be dropped.
 */
internal fun List<Long>.dropInitAddresses(
    qualifiedClassName: String,
    stackTrace: Array<String>,
    keepLast: Boolean = false
): List<Long> {
    val exceptionInit = "kfun:$qualifiedClassName#<init>"
    var dropCount = 0
    var foundInit = false
    for (i in stackTrace.indices) {
        if (stackTrace[i].contains(exceptionInit)) {
            foundInit = true
        } else if (foundInit) {
            dropCount = i
            break
        }
    }
    if (keepLast) dropCount--
    return drop(kotlin.math.max(0, dropCount))
}

/**
 * Returns a list containing all addresses expect for the last addresses that match with the [commonAddresses].
 */
internal fun List<Long>.dropCommonAddresses(
    commonAddresses: List<Long>
): List<Long> {
    var i = commonAddresses.size
    if (i == 0) return this
    return dropLastWhile {
        i-- >= 0 && commonAddresses[i] == it
    }
}
