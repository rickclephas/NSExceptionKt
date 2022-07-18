package com.rickclephas.kmp.nsexceptionkt.core

import kotlin.test.Test
import kotlin.test.assertEquals

class InitAddressesTests {

    private val qualifiedClassName = "my.app.CustomException"
    private val addresses = listOf<Long>(0, 1, 2, 3, 4, 5)
    private val stackTrace = arrayOf(
        "123 kfun:kotlin.Throwable#<init>(kotlin.String?){} + 24 abc",
        "456 kfun:kotlin.Exception#<init>(kotlin.String?){} + 5 def",
        "789 kfun:my.app.CustomException#<init>(kotlin.String?){} + 10 hij",
        "012 kfun:my.app.CustomException#<init>(){} + 12 klm",
        "345 kfun:my.app.class#function1(){} + 50 nop",
        "678 kfun:my.app.class#function2(){} + 60 qrs"
    )

    @Test
    fun testDropInit() {
        val withoutInitAddresses = addresses.dropInitAddresses(qualifiedClassName, stackTrace, false)
        assertEquals(listOf<Long>(4, 5), withoutInitAddresses)
    }

    @Test
    fun testDropInitKeepLast() {
        val withoutInitAddresses = addresses.dropInitAddresses(qualifiedClassName, stackTrace, true)
        assertEquals(listOf<Long>(3, 4, 5), withoutInitAddresses)
    }

    private fun testDropInitUnknownClassName(keepLast: Boolean) {
        val qualifiedClassName = "my.app.SomeOtherException"
        val withoutInitAddresses = addresses.dropInitAddresses(qualifiedClassName, stackTrace, keepLast)
        assertEquals(addresses, withoutInitAddresses)
    }

    @Test
    fun testDropInitUnknownClassNameDropLast() = testDropInitUnknownClassName(false)

    @Test
    fun testDropInitUnknownClassNameKeepLast() = testDropInitUnknownClassName(true)
}
