package com.rasugames.model

import android.R
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import com.rasugames.ui.components.Kcasac
import com.rasugames.ui.screens.Rasd
import com.rasugames.utils.core.setupOneView
import com.rasugames.game.vas

@SuppressLint("ViewConstructor")
class Basd(
    private val activity: ComponentActivity,
    private val viewClient: Rasd,
) : WebView(activity) {
    private val contentRoot: FrameLayout = FrameLayout(activity)
    val popupContainer: FrameLayout = FrameLayout(activity).apply {
        isVisible = false
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }
    val fullscreenContainer: FrameLayout = FrameLayout(activity).apply {
        isVisible = false
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }
    val content: ViewGroup = activity.findViewById(R.id.content)
    private var first = true
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (popupContainer.isNotEmpty()) {
                val top = popupContainer.getChildAt(popupContainer.childCount - 1) as WebView
                if (top.canGoBack()) {
                    top.goBack()
                } else {
                    top.stopLoading()
                    (top.parent as? ViewGroup)?.removeView(top)
                    top.destroy()
                    popupContainer.isVisible = popupContainer.isNotEmpty()
                }
                return
            }
            if (canGoBack()) {
                goBack()
            }
        }
    }


    private val chromeClient = Kcasac(activity, this, viewClient)

    init {
        Log.d("KKKKK", "CustomWebView init")

        content.addView(contentRoot)
        contentRoot.addView(
            this,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        contentRoot.addView(popupContainer)
        contentRoot.addView(fullscreenContainer)
        contentRoot.isVisible = false
        activity.onBackPressedDispatcher.addCallback(activity, backPressedCallback)

        setupOneView(this, viewClient, chromeClient)
    }

    fun showOneWebView() {
        if (first) {
            first = false
            for (i in content.childCount - 1 downTo 0) {
                val child = content.getChildAt(i)
                if (child != contentRoot) content.removeViewAt(i)
            }

            if (contentRoot.parent == null) {
                activity.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                content.addView(contentRoot)
                vas(activity.activityResultRegistry)
            }
        }

        if (!contentRoot.isVisible)
            contentRoot.isVisible = true

        Log.d("KKKKK", "showWebView: ${contentRoot.isVisible}")
    }

    fun getW(): WebView {
        return this
    }
}
