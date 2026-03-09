package com.rasugames.navigation

sealed class Screen {
    data object Menu : Screen()
    data object Levels : Screen()
    data class Game(val level: Int) : Screen()
    data object Leaderboard : Screen()
    data object HowToPlay : Screen()
    data object PrivacyPolicy : Screen()
}
