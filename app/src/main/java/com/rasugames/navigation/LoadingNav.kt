package com.rasugames.navigation

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rasugames.ui.screens.ConnectScreen
import com.rasugames.ui.screens.LoadingScreen
import com.rasugames.utils.core.RouteManager

@SuppressLint("ContextCastToActivity")
@Composable
fun LoadingGraph(
    route: RouteManager,
    act: ComponentActivity,
    onNext: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Loading.route
    ) {
        composable(Screens.Loading.route) {
            val screen by NavigationStore.currentScreen.collectAsState()
            when (screen) {
                ScreenNav.Loading -> LoadingScreen(route, act)
                ScreenNav.Move -> StartMenu {
                    onNext()
                }
                ScreenNav.NoConnection -> ConnectScreen()
                else -> {}
            }
        }

        composable(Screens.Connect.route) {
            ConnectScreen()
        }
    }
}

@Composable
fun StartMenu(onMove: () -> Unit) {
    LaunchedEffect(Unit) {
        onMove()
    }
}
