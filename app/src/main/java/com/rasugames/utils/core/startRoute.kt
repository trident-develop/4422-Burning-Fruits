package com.rasugames.utils.core

import androidx.activity.ComponentActivity
import com.rasugames.utils.NotFoundException
import com.rasugames.ui.components.Save.getUrl
import com.rasugames.game.onEx
import com.rasugames.ui.screens.progress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun startRoute(activity: ComponentActivity, startD: RouteManager) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url = getUrl(activity)
            progress(
                savedUrl = url,
                startD
            )
        } catch (e: NotFoundException) {
            startD.getR()
            onEx(activity, startD)
        }
    }
}
