# NSExceptionKt for Firebase Crashlytics

## Installation

First make sure you have [set up](https://firebase.google.com/docs/crashlytics/get-started?platform=ios) Crashlytics (v9.3.0 or above).  

After that add and [export](https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#export-dependencies-to-binaries) 
the Kotlin dependency to your `appleMain` source set.

```kotlin
kotlin {
    iosArm64 { // and/or any other Apple target 
        binaries.framework {
            isStatic = true // it's recommended to use a static framework
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
the `NSExceptionKtCrashlytics` dependency and update your Firebase configuration logic with a call to `NSExceptionKt.addReporter`:

```swift
import Firebase
import NSExceptionKtCrashlytics
import shared // This is your shared Kotlin module

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        NSExceptionKt.addReporter(.crashlytics(causedByStrategy: .append))
        return true
    }
}
```

That's all, now go and crash that app!

## Implementation details

The Firebase Crashlytics iOS SDK has a limited API, which means it ([currently](https://github.com/firebase/firebase-ios-sdk/issues/10030))
doesn't support the concept of caused by exceptions.

NSExceptionKt provides 3 different strategies to handle this:
```swift
public enum CausedByStrategy {
    /// Causes will be ignored, only the main Throwable is logged as a fatal exception.
    case ignore
    /// Causes are appended to the main Throwable and logged as a single fatal exception.
    case append
    /// All causes are logged as non-fatal exceptions before the main Throwable is logged as a fatal exception.
    case logNonFatal
}
```

Crashlytics only stores a single fatal exception, so the final Kotlin termination won't be recorded.
