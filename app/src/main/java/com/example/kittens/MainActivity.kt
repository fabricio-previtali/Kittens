package com.example.kittens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.kittens.ui.CatsApp
import com.example.kittens.ui.theme.CatsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CatsApp()
                }
            }
        }
    }
}
