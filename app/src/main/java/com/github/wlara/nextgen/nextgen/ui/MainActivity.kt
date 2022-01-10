package com.github.wlara.nextgen.nextgen.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.wlara.nextgen.nextgen.ui.home.HomeScreen
import com.github.wlara.nextgen.nextgen.ui.theme.NextGenTheme
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
