package com.rickclephas.kmp.nsexceptionkt.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class CommonAddressesTests {

    @Test
    fun testDropCommon() {
        val commonAddresses = listOf<Long>(5, 4, 3, 2, 1, 0)
        val addresses = listOf<Long>(8, 7, 6, 2, 1, 0)
        val withoutCommonAddresses = addresses.dropCommonAddresses(commonAddresses)
        assertEquals(listOf<Long>(8, 7, 6), withoutCommonAddresses)
    }

    @Test
    fun testDropCommonEmptyCommon() {
        val addresses = listOf<Long>(0, 1, 2)
        val withoutCommonAddresses = addresses.dropCommonAddresses(emptyList())
        assertSame(addresses, withoutCommonAddresses)
    }

    @Test
    fun testDropCommonSameAddresses() {
        val addresses = listOf<Long>(0, 1, 2)
        val withoutCommonAddresses = addresses.dropCommonAddresses(addresses)
        assertEquals(emptyList(), withoutCommonAddresses)
    }
}
