# NSExceptionKt for Bugsnag

## Installation

First make sure you have [set up](https://docs.bugsnag.com/platforms/ios/#installation) Bugsnag (v6.22.1 or above).  

After that add and export the Kotlin dependency to your `appleMain` source set.

```kotlin
kotlin {
    iosArm64 {
        binaries.framework {
            isStatic = true
            export("com.rickclephas.kmp:nsexception-kt-core:<version>")
        }
    }
    sourceSets {
        appleMain {
            dependencies {
                api("com.rickclephas.kmp:nsexception-kt-core:<version>")
            }
        }
    }
}
```

Now in your Xcode project [add](https://developer.apple.com/documentation/xcode/adding-package-dependencies-to-your-app) 
the `NSExceptionKtBugsnag` dependency and update your Bugsnag configuration logic with a call to `NSExceptionKt.addReporter`:

```swift
import Bugsnag
import NSExceptionKtBugsnag
import shared // This is your shared Kotlin module

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        let config = BugsnagConfiguration.loadConfig()
        NSExceptionKt.addReporter(.bugsnag(config))
        Bugsnag.start(with: config)
        return true
    }
}
```

That's all, now go and crash that app!

## Implementation details

Bugsnag has built-in support for caused by errors. 

However, Bugsnag does store multiple fatal exceptions, which would normally result in a logged fatal Kotlin termination.
To prevent this fatal Kotlin termination from being logged NSExceptionKt sets a feature flag after 
the unhandled Kotlin exception has been logged, an `addOnSendError` filter makes sure to drop the errors contain said feature flag. 
