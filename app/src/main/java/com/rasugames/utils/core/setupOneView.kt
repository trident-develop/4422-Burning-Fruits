package com.rasugames.utils.core

import android.annotation.SuppressLint
import android.view.View.LAYER_TYPE_HARDWARE
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import com.rasugames.ui.components.Kcasac
import com.rasugames.ui.screens.Rasd

@Suppress("DEPRECATION")
@SuppressLint("SetJavaScriptEnabled")
fun setupOneView(
    webView: WebView,
    viewClient: Rasd,
    chromeClient: Kcasac,
) {
    webView.webViewClient = viewClient
    webView.webChromeClient = chromeClient
    webView.isFocusable = true
    webView.isFocusableInTouchMode = true
    webView.settings.javaScriptEnabled = true
    webView.settings.javaScriptCanOpenWindowsAutomatically = true
    webView.settings.builtInZoomControls = true
    webView.settings.displayZoomControls = false
    webView.settings.setSupportMultipleWindows(true)
    webView.settings.mediaPlaybackRequiresUserGesture = true
    webView.settings.databaseEnabled = true
    webView.settings.domStorageEnabled = true
    CookieManager.getInstance().setAcceptCookie(true)
    CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
    webView.settings.loadWithOverviewMode = true
    webView.settings.useWideViewPort = true
    webView.settings.setSupportZoom(true)
    webView.isVerticalScrollBarEnabled = false
    webView.isHorizontalScrollBarEnabled = false
    webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    webView.settings.allowContentAccess = true
    webView.settings.allowFileAccess = true
    webView.settings.allowFileAccessFromFileURLs = false
    webView.settings.allowUniversalAccessFromFileURLs = false
    webView.settings.blockNetworkImage = false
    webView.settings.blockNetworkLoads = false
    webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
    webView.settings.loadsImagesAutomatically = true
    webView.setInitialScale(0)
    webView.settings.setNeedInitialFocus(true)
    webView.settings.offscreenPreRaster = false
    webView.settings.saveFormData = true
    webView.setLayerType(
        LAYER_TYPE_HARDWARE,
        null
    )
    webView.settings.userAgentString = webView.settings.userAgentString.replace(
        Regex("(; wv|Version/\\S+\\s)"),
        ""
    )
}
