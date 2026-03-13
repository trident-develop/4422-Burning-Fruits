package com.rasugames.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NavigationStore {

    private val _currentScreen = MutableStateFlow<ScreenNav>(ScreenNav.Loading)
    val currentScreen: StateFlow<ScreenNav> = _currentScreen

    fun navigate(screen: ScreenNav) {
        _currentScreen.value = screen
    }
}

sealed class ScreenNav {
    data object Splash : ScreenNav()
    data object Menu : ScreenNav()
    data object Move : ScreenNav()
    data object NoConnection : ScreenNav()
    data object Game : ScreenNav()
    data object Settings : ScreenNav()
    data object Loading : ScreenNav()
    data class Details(val id: String) : ScreenNav()
}
