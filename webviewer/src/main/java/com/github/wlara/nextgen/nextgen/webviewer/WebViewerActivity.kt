package com.github.wlara.nextgen.nextgen.webviewer

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.viewinterop.AndroidView
import com.github.wlara.nextgen.nextgen.webviewer.ui.theme.WebViewerTheme

// This Activity is used just to demonstrate that we can navigate to
// an external Activity from our Navigation Graph at the app module.
class WebViewerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(EXTRA_URL)!!
        setContent {
            WebViewerTheme {
                AndroidView(
                    factory = {
                        WebView(this).apply {
                            webViewClient = WebViewClient()
                            loadUrl(url)
                        }
                    }
                )
            }
        }
    }

    companion object {
        const val EXTRA_URL = "EXTRA_URL"
    }
}
