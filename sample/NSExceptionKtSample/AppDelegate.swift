import Foundation
import UIKit
import Bugsnag
import Firebase
import NSExceptionKtCrashlytics
import Sentry
import shared

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        
        // Setup Bugsnag
        let config = BugsnagConfiguration.loadConfig()
        Bugsnag.start(with: config)
        
        // Setup Firebase Craslytics
        FirebaseApp.configure()
        NSExceptionKt.addReporter(.crashlytics(causedByStrategy: .logNonFatal))
        
        // Setup Sentry
        SentrySDK.start { options in
            options.dsn = Bundle.main.object(forInfoDictionaryKey: "SENTRY_DSN") as? String
            options.debug = true
            options.tracesSampleRate = 1.0
        }
        
        return true
    }
}
