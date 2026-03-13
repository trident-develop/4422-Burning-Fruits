package com.rasugames.ui.screens

import com.rasugames.utils.core.RouteManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun progress(
    savedUrl: String,
    cwv: RouteManager
) {
    withContext(Dispatchers.Main) {
        val w = cwv.getOneV().getW()
        w.requestFocus()
        w.loadUrl(savedUrl)
    }
}
