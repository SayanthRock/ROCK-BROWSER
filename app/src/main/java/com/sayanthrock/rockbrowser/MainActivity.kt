package com.sayanthrock.rockbrowser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sayanthrock.rockbrowser.ui.BrowserApp
class MainActivity : ComponentActivity() { override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { BrowserApp() } } }
