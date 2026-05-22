package com.rickclephas.kmp.nsexceptionkt.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController

@Composable
fun CrashView() = Surface {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(Greeting().greeting())
        Button(onClick = {
            fatalGeneralStep1()
        }) {
            Text("Crash")
        }
    }
}

fun CrashViewController() = ComposeUIViewController {
    MaterialTheme {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CrashView()
        }
    }
}

private fun fatalGeneralStep1() = fatalGeneralStep2()
private fun fatalGeneralStep2() = fatalGeneralStep3()
private fun fatalGeneralStep3() {
    error("Test crash from general function")
}
