package com.rickclephas.kmp.nsexceptionkt.core

@RequiresOptIn(
    message = "Internal NSExceptionKt API that shouldn't be used outside NSExceptionKt",
    level = RequiresOptIn.Level.WARNING
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
public annotation class InternalNSExceptionKtApi
