# NSExceptionKt for Sentry

> Status: **experimental** 🚧  
> The basic scenarios have been tested, but the implementation hasn't been battle-tested just yet.

> ⚠️ This implementation heavily uses private APIs from the Sentry Cocoa SDK ⚠️

## Installation

First make sure you have [set up](https://docs.sentry.io/platforms/apple/guides/ios/#install) Sentry (v7.19.0 or above).  
After that add the following dependency to your `iosMain` or `appleMain` source set.

```kotlin
implementation("com.rickclephas.kmp:nsexception-kt-sentry:<version>")
```

and create the `dropSentryKotlinCrashEvent` and `setupSentry` functions (e.g. in your `AppInit.kt` file):

```kotlin
import com.rickclephas.kmp.nsexceptionkt.sentry.cinterop.SentryEvent

fun dropSentryKotlinCrashEvent(event: SentryEvent?): SentryEvent? {
    return dropKotlinCrashEvent(event)
}

fun setupSentry() {
    setSentryUnhandledExceptionHook()
}
```

> Note: `dropKotlinCrashEvent` and `setSentryUnhandledExceptionHook` are only available for Apple targets,
> so you can't create `dropSentryKotlinCrashEvent` or `setupSentry` in `commonMain`.

Now go to your Xcode project and update your `AppDelegate`:

```swift
class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        SentrySDK.start { options in
            options.dsn = "https://examplePublicKey@o0.ingest.sentry.io/0"
            options.beforeSend = { event in
                return AppInitKt.dropSentryKotlinCrashEvent(event: event)
            }
        }
        AppInitKt.setupSentry() // should be called after configuring Sentry
        return true
    }
}
```

That's all, now go and crash that app!

## Caused by exceptions

Sentry has built-in support for caused by errors, so no additional configuration is required.

## Filtering the Kotlin termination

Sentry stores multiple fatal exceptions, so make sure to update call the `dropSentryKotlinCrashEvent`
function mentioned above in your `beforeSend`.

> Internally NSExceptionKt sets a tag after logging the unhandled Kotlin exception.  
> This tag is used to stop the fatal Kotlin termination from being sent to Sentry.
