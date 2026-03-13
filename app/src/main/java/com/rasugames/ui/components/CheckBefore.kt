package com.rasugames.ui.components

import android.content.Context
import com.rasugames.navigation.NavigationStore.navigate
import com.rasugames.navigation.ScreenNav
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckBefore(data: String?, context: Context, onClose: () -> Unit) {
    init {
        val hand = DomHand()
        data?.let { d ->
            when {
                hand.short == d.take(hand.short.length) -> {
                    navigate(ScreenNav.Move)
                }

                d.length < 3 -> {
                    throw IllegalStateException()
                }

                hand.short != d.take(hand.short.length) -> {
                    onClose()
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            Save.saveStringOnce(context, d)
                        } catch (_: Exception) {
                        }
                    }
                }

                else -> {}
            }
        }
    }
}
