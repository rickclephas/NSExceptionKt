package com.rickclephas.kmp.nsexceptionkt.core

import kotlin.experimental.ExperimentalNativeApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalNativeApi::class)
class ThrowableCausesTests {

    @Test
    fun testNoCauses() {
        assert(Throwable().causes.isEmpty())
    }

    @Test
    fun testSingleCause() {
        val cause = Throwable("Cause throwable")
        val throwable = Throwable("Test throwable", cause)
        assertEquals(listOf(cause), throwable.causes)
    }

    @Test
    fun testMultipleCauses() {
        val cause1 = Throwable("Cause 1 throwable")
        val cause2 = Throwable("Cause 2 throwable", cause1)
        val throwable = Throwable("Test throwable", cause2)
        assertEquals(listOf(cause2, cause1), throwable.causes)
    }

    private class MyThrowable(override val message: String?) : Throwable() {
        override var cause: Throwable? = null
    }

    @Test
    fun testReferenceCycle() {
        val cause = MyThrowable("Cause throwable")
        val throwable = Throwable("Test throwable", cause)
        cause.cause = throwable
        assertEquals(listOf(cause, throwable), throwable.causes)
    }
}
