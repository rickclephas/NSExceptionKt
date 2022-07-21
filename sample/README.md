# NSExceptionKt Sample

This is a sample Kotlin Multiplatform iOS application with a crash button.

Make sure to configure your crash reporting tools:

* Add your `GoogleService-Info.plist` to `NSExceptionKtSample/GoogleService-Info.plist`
* Create `NSExceptionKtSample/NSExceptionKtSample.xcconfig` with the following contents:
```
SENTRY_ORG = YOUR_ORG
SENTRY_PROJECT = YOUR_PROJECT
SENTRY_AUTH_TOKEN = YOUR_AUTH_TOKEN
SENTRY_DSN = YOUR_DSN

BUGSNAG_API_KEY = YOUR_API_KEY
```
