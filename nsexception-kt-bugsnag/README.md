# NSExceptionKt for Bugsnag

> Status: **experimental** 🚧  
> The basic scenarios have been tested, but the implementation hasn't been battle-tested just yet.

## Installation

First make sure you have [set up](https://docs.bugsnag.com/platforms/ios/#installation) Bugsnag.  
After that add the following dependency to your `iosMain` or `appleMain` source set.

```kotlin
implementation("com.rickclephas.kmp:nsexception-kt-bugsnag:<version>")
```

and create the `updateBugsnagConfig` and `setupBugsnag` functions (e.g. in your `AppInit.kt` file):

```kotlin
fun updateBugsnagConfig(config: BugsnagConfiguration) {
    configureBugsnag(config)
}

fun setupBugsnag() {
    setBugsnagUnhandledExceptionHook()
}
```

> Note: `configureBugsnag` and `setBugsnagUnhandledExceptionHook` are only available for Apple targets,
> so you can't create `updateBugsnagConfig` or `setupBugsnag` in `commonMain`.

Now go to your Xcode project and update your `AppDelegate`:

```swift
class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        let config = BugsnagConfiguration.loadConfig()
        AppInitKt.updateBugsnagConfig(config: config)
        Bugsnag.start(with: config)
        AppInitKt.setupBugsnag() // should be called after configuring Bugsnag
    }
}
```

That's all, now go and crash that app!

## Caused by exceptions

Bugsnag has built-in support for caused by errors, so no addition configuration is required.

## Filtering the Kotlin termination

Bugsnag stores multiple fatal exceptions, so make sure to update your `BugsnagConfiguration` with the `configureBugsnag`
function mentioned above.

> Internally NSExceptionKt sets a feature flag after logging the unhandled Kotlin exception.  
> This feature flag is used to stop the fatal Kotlin termination from being sent to Bugsnag.
