package com.rickclephas.kmp.nsexceptionkt.sample

import com.rickclephas.kmp.nsexceptionkt.bugsnag.cinterop.BugsnagConfiguration
import com.rickclephas.kmp.nsexceptionkt.bugsnag.configureBugsnag
import com.rickclephas.kmp.nsexceptionkt.bugsnag.setBugsnagUnhandledExceptionHook
import com.rickclephas.kmp.nsexceptionkt.crashlytics.CausedByStrategy
import com.rickclephas.kmp.nsexceptionkt.crashlytics.setCrashlyticsUnhandledExceptionHook
import com.rickclephas.kmp.nsexceptionkt.sentry.cinterop.SentryEvent
import com.rickclephas.kmp.nsexceptionkt.sentry.dropKotlinCrashEvent
import com.rickclephas.kmp.nsexceptionkt.sentry.setSentryUnhandledExceptionHook

fun updateBugsnagConfig(config: BugsnagConfiguration) {
    configureBugsnag(config)
}

fun setupBugsnag() {
    setBugsnagUnhandledExceptionHook()
}

fun setupCrashlytics() {
    setCrashlyticsUnhandledExceptionHook(CausedByStrategy.APPEND)
}

fun dropSentryKotlinCrashEvent(event: SentryEvent?): SentryEvent? {
    return dropKotlinCrashEvent(event)
}

fun setupSentry() {
    setSentryUnhandledExceptionHook()
}
