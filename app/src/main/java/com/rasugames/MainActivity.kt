package com.rasugames

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.rasugames.data.GamePreferences
import com.rasugames.navigation.Screen
import com.rasugames.ui.screens.GameScreen
import com.rasugames.ui.screens.HowToPlayScreen
import com.rasugames.ui.screens.LeaderboardScreen
import com.rasugames.ui.screens.LevelsScreen
import com.rasugames.ui.screens.MenuScreen
import com.rasugames.ui.screens.PrivacyPolicyScreen

class MainActivity : ComponentActivity() {

    private val windowController by lazy {
        WindowInsetsControllerCompat(window, window.decorView)
    }
    private var multiTouchDetected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefs = GamePreferences(this)

        setContent {
            var currentScreen: Screen by remember { mutableStateOf(Screen.Menu) }

            when (val screen = currentScreen) {
                Screen.Menu -> MenuScreen(
                    onPlay = { currentScreen = Screen.Levels },
                    onLeaderboard = { currentScreen = Screen.Leaderboard },
                    onHowToPlay = { currentScreen = Screen.HowToPlay },
                    onPrivacyPolicy = { currentScreen = Screen.PrivacyPolicy }
                )
                Screen.Levels -> LevelsScreen(
                    prefs = prefs,
                    onBack = { currentScreen = Screen.Menu },
                    onLevelSelected = { level -> currentScreen = Screen.Game(level) }
                )
                is Screen.Game -> GameScreen(
                    level = screen.level,
                    prefs = prefs,
                    onHome = { currentScreen = Screen.Menu },
                    onBack = { currentScreen = Screen.Levels },
                    onNextLevel = { nextLevel ->
                        currentScreen = if (nextLevel <= 21) Screen.Game(nextLevel) else Screen.Levels
                    }
                )
                Screen.Leaderboard -> LeaderboardScreen(
                    prefs = prefs,
                    onBack = { currentScreen = Screen.Menu }
                )
                Screen.HowToPlay -> HowToPlayScreen(
                    onBack = { currentScreen = Screen.Menu }
                )
                Screen.PrivacyPolicy -> PrivacyPolicyScreen(
                    onBack = { currentScreen = Screen.Menu }
                )

                else -> {}
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.pointerCount > 1) {
            if (!multiTouchDetected) {
                multiTouchDetected = true
                val cancelEvent = MotionEvent.obtain(ev)
                cancelEvent.action = MotionEvent.ACTION_CANCEL
                super.dispatchTouchEvent(cancelEvent)
                cancelEvent.recycle()
            }
            return true
        }
        if (multiTouchDetected) {
            if (ev.actionMasked == MotionEvent.ACTION_UP ||
                ev.actionMasked == MotionEvent.ACTION_CANCEL
            ) {
                multiTouchDetected = false
            }
            return true
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onResume() {
        super.onResume()
        windowController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowController.hide(WindowInsetsCompat.Type.systemBars())
    }
}
