//
//  AppDelegate.swift
//  NSExceptionKtSample
//
//  Created by Rick Clephas on 21/07/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import UIKit
import Bugsnag
import Firebase
import Sentry
import shared

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        
        let config = BugsnagConfiguration.loadConfig()
        AppInitKt.updateBugsnagConfig(config: config)
        Bugsnag.start(with: config)
        AppInitKt.setupBugsnag()
        
        FirebaseApp.configure()
        AppInitKt.setupCrashlytics()
        
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
