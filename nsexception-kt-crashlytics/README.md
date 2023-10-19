# NSExceptionKt for Firebase Crashlytics

> Status: **experimental** 🚧  
> The basic scenarios have been tested, but the implementation hasn't been battle-tested just yet.

## Installation

First make sure you have [set up](https://firebase.google.com/docs/crashlytics/get-started?platform=ios) 
Crashlytics (v6.21.0 or above).  
After that add the following dependency to your `iosMain` or `appleMain` source set.

```kotlin
implementation("com.rickclephas.kmp:nsexception-kt-crashlytics:<version>")
```

and create the `setupCrashlytics` function (e.g. in your `AppInit.kt` file):

```kotlin
fun setupCrashlytics() {
    setCrashlyticsUnhandledExceptionHook()
}
```

> [!IMPORTANT]
> `setCrashlyticsUnhandledExceptionHook` is only available for Apple targets,
> so you can't create `setupCrashlytics` in `commonMain`.

Now go to your Xcode project and update your `AppDelegate`:

```swift
class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        AppInitKt.setupCrashlytics() // should be called after configuring Firebase
        return true
    }
}
```

That's all, now go and crash that app!

## Caused by exceptions

The Firebase Crashlytics iOS SDK has a limited API,
which means it ([currently](https://github.com/firebase/firebase-ios-sdk/issues/10030)) 
doesn't support the concept of caused by exceptions.

By default, only the main exception is logged and any causes are ignored.  
However `setCrashlyticsUnhandledExceptionHook` accepts a `CausedByStrategy` to customise that behaviour:

```kotlin
public enum class CausedByStrategy {
    /**
     * Causes will be ignored,
     * only the main [Throwable] is logged as a fatal exception.
     */
    IGNORE, // This is the default
    /**
     * Causes are appended to the main [Throwable]
     * and logged as a single fatal exception.
     */
    APPEND,
    /**
     * All causes are logged as non-fatal exceptions
     * before the main [Throwable] is logged as a fatal exception.
     */
    LOG_NON_FATAL
}
```

## Filtering the Kotlin termination

Crashlytics only stores a single fatal exception, so the final Kotlin termination won't be recorded.
