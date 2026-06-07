package com.example.pressandpull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.example.pressandpull.data.FitnessDatabase
import com.example.pressandpull.ui.PressPullApp
import com.example.pressandpull.ui.theme.PressAndPullTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PressAndPullTheme {
                PressPullApp(database = remember { FitnessDatabase(applicationContext) })
            }
        }
    }
}

