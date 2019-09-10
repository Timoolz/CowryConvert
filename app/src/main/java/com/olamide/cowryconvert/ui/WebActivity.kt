package com.olamide.cowryconvert.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.olamide.cowryconvert.AppConstants
import com.olamide.cowryconvert.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.getStringExtra(AppConstants.BUNDLE_URL) != null && !intent.getStringExtra(AppConstants.BUNDLE_URL).isBlank()) {
            setContentView(R.layout.activity_web)

            webView.visibility = View.VISIBLE
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()

            webView.loadUrl(intent.getStringExtra(AppConstants.BUNDLE_URL))

        } else {
            finish()
        }

    }
}
