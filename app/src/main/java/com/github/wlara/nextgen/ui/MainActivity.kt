package com.github.wlara.nextgen.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.wlara.nextgen.ui.home.HomeScreen
import com.github.wlara.nextgen.ui.theme.NextGenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NextGenTheme {
                HomeScreen()
            }
        }
    }
}
