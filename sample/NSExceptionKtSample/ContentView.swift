import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greeting()

	var body: some View {
        VStack {
            Text(greet)
            Button {
                CrashKt.throwException()
            } label: {
                Text("Crash")
            }

        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
