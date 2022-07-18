package com.rickclephas.kmp.nsexceptionkt.core

import kotlin.test.Test
import kotlin.test.assertEquals

class ThrowableNameTests {

    private class MyThrowable: Throwable()

    @Test
    fun testMyThrowableName() {
        val exception = MyThrowable()
        assertEquals("com.rickclephas.kmp.nsexceptionkt.core.ThrowableNameTests.MyThrowable", exception.name)
    }

    @Test
    fun testIllegalArgumentExceptionName() {
        val exception = IllegalArgumentException()
        assertEquals("kotlin.IllegalArgumentException", exception.name)
    }

    @Test
    fun testLocalThrowableName() {
        class MyLocalThrowable: Throwable()
        val throwable = MyLocalThrowable()
        assertEquals("MyLocalThrowable", throwable.name)
    }

    @Test
    fun testAnonymousThrowableName() {
        val throwable = object : Throwable() { }
        assertEquals("Throwable", throwable.name)
    }
}
