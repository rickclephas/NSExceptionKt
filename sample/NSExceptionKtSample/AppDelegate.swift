import Foundation
import UIKit
import Bugsnag
import NSExceptionKtBugsnag
import Firebase
import NSExceptionKtCrashlytics
import shared

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        
        // Setup Bugsnag
        let config = BugsnagConfiguration.loadConfig()
        NSExceptionKt.addReporter(.bugsnag(config))
        Bugsnag.start(with: config)
        
        // Setup Firebase Craslytics
        FirebaseApp.configure()
        NSExceptionKt.addReporter(.crashlytics(causedByStrategy: .append))
        
        return true
    }
}
