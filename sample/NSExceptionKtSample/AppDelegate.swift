import Foundation
import UIKit
import Bugsnag
import Firebase
import Sentry
import shared

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        
        // Setup Bugsnag
        let config = BugsnagConfiguration.loadConfig()
        AppInitKt.updateBugsnagConfig(config: config)
        Bugsnag.start(with: config)
        AppInitKt.setupBugsnag()
        
        // Setup Firebase Craslytics
        FirebaseApp.configure()
        AppInitKt.setupCrashlytics()
        
        // Setup Sentry
        SentrySDK.start { options in
            options.dsn = Bundle.main.object(forInfoDictionaryKey: "SENTRY_DSN") as? String
            options.debug = true
            options.tracesSampleRate = 1.0
            options.beforeSend = { event in
                return AppInitKt.dropSentryKotlinCrashEvent(event: event)
            }
        }
        AppInitKt.setupSentry()
        
        return true
    }
}
