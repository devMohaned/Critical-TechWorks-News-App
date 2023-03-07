package com.criticaltechworks.criticaltechworksnewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.fragment.app.FragmentActivity
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.navigation.NavigationHost
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.theme.CriticalTechWorksNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CriticalTechWorksNewsAppTheme {
                    val windowSizeClass = calculateWindowSizeClass(this)
                    NavigationHost(windowSize = windowSizeClass.widthSizeClass)
                }
        }
    }
}
