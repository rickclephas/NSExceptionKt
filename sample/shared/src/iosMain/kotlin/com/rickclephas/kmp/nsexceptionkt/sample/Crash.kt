package com.rickclephas.kmp.nsexceptionkt.sample

class TestException(
    message: String?,
    cause: Throwable?
): IllegalArgumentException(message, cause)

fun throwException() {
    return try {
        throwCauseException()
    } catch (e: Throwable) {
        throw TestException("Test exception 2", e)
    }
}

private fun throwCauseException() {
    throw IllegalArgumentException("Test exception 1")
}
