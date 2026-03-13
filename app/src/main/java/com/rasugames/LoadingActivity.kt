package com.rasugames

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.rasugames.navigation.LoadingGraph
import com.rasugames.utils.core.RouteManager
import com.rasugames.ui.screens.newPost

class LoadingActivity : ComponentActivity() {

    private var controller: WindowInsetsControllerCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        controller = WindowInsetsControllerCompat(window, window.decorView)
        controller?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller?.hide(WindowInsetsCompat.Type.systemBars())
        enableEdgeToEdge()
        val route = RouteManager(this)
        newPost(intent)
        setContent {
            LoadingGraph(
                route,
                this@LoadingActivity
            ) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
