# NSExceptionKt

A Kotlin Multiplatform Library to improve crash reports on Apple platforms.

## Installation

Checkout the implementation specific `README`s for usage and installation details:

* [Crashlytics](nsexception-kt-crashlytics/README.md)
* [Bugsnag](nsexception-kt-bugsnag/README.md)
* [Sentry](nsexception-kt-sentry/README.md)

## Why this library?

If you have been developing applications for Apple platforms with Kotlin Native, 
then you have likely encountered crashes like the following:

```
Function doesn't have or inherit @Throws annotation and thus exception isn't propagated from Kotlin to Objective-C/Swift as NSError.
It is considered unexpected and unhandled instead. Program will be terminated.
Uncaught Kotlin exception: com.rickclephas.myapplication.Platform.TestException: Test exception 2
    at 0   iosApp                              0x1020e88e7        kfun:kotlin.Exception#<init>(kotlin.String?;kotlin.Throwable?){} + 119 
    at 1   iosApp                              0x1020e8b1b        kfun:kotlin.RuntimeException#<init>(kotlin.String?;kotlin.Throwable?){} + 119 
    at 2   iosApp                              0x1020e8eff        kfun:kotlin.IllegalArgumentException#<init>(kotlin.String?;kotlin.Throwable?){} + 119 
    at 3   iosApp                              0x1020d3273        kfun:com.rickclephas.myapplication.Platform.TestException#<init>(kotlin.String?;kotlin.Throwable?){} + 119 
    ... and 38 more stack frames
Caused by: kotlin.IllegalArgumentException: Test exception 1
    at 0   iosApp                              0x1020ee457        kfun:kotlin.Throwable#<init>(kotlin.String?){} + 95 
    at 1   iosApp                              0x1020e884b        kfun:kotlin.Exception#<init>(kotlin.String?){} + 91 
    at 2   iosApp                              0x1020e8a7f        kfun:kotlin.RuntimeException#<init>(kotlin.String?){} + 91 
    at 3   iosApp                              0x1020e8e63        kfun:kotlin.IllegalArgumentException#<init>(kotlin.String?){} + 91
    ... and 39 more stack frames
```

So far so good. You fix the crash and all is well. But what happens when such a crash occurs in production?!  
Well, just like the above message said, the program will be [terminated](https://github.com/JetBrains/kotlin/blob/02901aeb106146274df7cff5686f8e376652fe2a/kotlin-native/runtime/src/main/cpp/Exceptions.cpp#L92).  
And that is probably what you want anyway, so you open your favorite crash reporting tool and...:

```
Exception Type: EXC_CRASH (SIGABRT)
Crashed Thread: 0

Thread 0 Crashed:
0   libsystem_kernel.dylib          0x3980b2e60         __pthread_kill
1   libsystem_pthread.dylib         0x39815b3bc         pthread_kill
2   libsystem_c.dylib               0x30018f3f4         abort
3   iosApp                          0x20230fc2c         konan::abort
4   iosApp                          0x20231dca0         (anonymous namespace)::terminateWithUnhandledException::lambda::operator()
5   iosApp                          0x20231db64         (anonymous namespace)::lambda::operator()<T>
6   iosApp                          0x20231d924         (anonymous namespace)::terminateWithUnhandledException
7   iosApp                          0x20231d8ac         (anonymous namespace)::processUnhandledException
8   iosApp                          0x20231f73c         kotlin::ProcessUnhandledException
9   iosApp                          0x202322cb4         Kotlin_ObjCExport_trapOnUndeclaredException
```

The good news: you know your app crashed 🥸. The bad news: there is no useful stacktrace 🥹.

Fortunately we can [log](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.native/set-unhandled-exception-hook.html) 
such unhandled exceptions in Kotlin before the app is terminated! 

## The challenges

While we can use any Objective-C crash reporting SDK we like, 
the concept of error handling is very different between Kotlin and ObjC/Swift.

> On the Kotlin side all exceptions are unchecked, but on the Swift side they are all checked.  
> That's why we need that [`@Throws` annotation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throws/), 
> which tells Kotlin what exception types a function is expected to throw.

When you try to log your unhandled `Exception`s, you'll soon realise that most SDKs only expose APIs to log `NSError`s.
Unfortunately you can't just map an `Exception` to a `NSError`, for one you would lose the stacktrace.

In the rare case that you can log your exception (e.g. with Crashlytics via the [ExceptionModel] class),
the logged exception will be marked as non-fatal and the real crash is logged as well (resulting in two reports).

[ExceptionModel]: https://firebase.google.com/docs/reference/swift/firebasecrashlytics/api/reference/Classes/ExceptionModel

So we basically need to:
* log a fatal error from Kotlin
* attach a stacktrace to it
* preferably attach the caused by exceptions (with their stacktrace)
* prevent the Kotlin termination from being logged 

## Acknowledgments

[Kermit](https://github.com/touchlab/Kermit) by [Touchlab](https://touchlab.co/) 
and specifically [@kpgalligan](https://twitter.com/kpgalligan) have been a great inspiration for this project.

I would also like to thank Firebase, Bugsnag and Sentry for publishing the sources of their SDKs.  
Without those sources projects like these wouldn't exist.
