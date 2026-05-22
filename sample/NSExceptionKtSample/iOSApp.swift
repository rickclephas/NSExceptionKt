import SwiftUI
import shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
	var body: some Scene {
		WindowGroup {
            NavigationView {
                VStack {
                    NavigationLink {
                        ContentView()
                    } label: {
                        Text("SwiftUI")
                    }
                    NavigationLink {
                        ComposeViewController()
                    } label: {
                        Text("Compose")
                    }
                }
            }
		}
	}
}

private struct ComposeViewController: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> some UIViewController {
        ComposeViewKt.CrashViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        
    }
}
