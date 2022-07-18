package com.rickclephas.kmp.nsexceptionkt.core

public fun Throwable.getFilteredStackTraceAddresses(
    keepLastInit: Boolean = false,
    commonAddresses: List<Long> = emptyList()
): List<Long> = getStackTraceAddresses().dropInitAddresses(
    qualifiedClassName = this::class.qualifiedName ?: Throwable::class.qualifiedName!!,
    stackTrace = getStackTrace(),
    keepLast = keepLastInit
).dropCommonAddresses(commonAddresses)

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

internal fun List<Long>.dropCommonAddresses(
    commonAddresses: List<Long>
): List<Long> {
    var i = commonAddresses.size
    if (i == 0) return this
    return dropLastWhile {
        i-- >= 0 && commonAddresses[i] == it
    }
}
