package com.rasugames.ui.screens

import android.content.Context
import android.os.Build

fun ps(context: Context): Int? = runCatching {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val packageManager = context.packageManager
        val sourceInfo = packageManager.getInstallSourceInfo(context.packageName)
        sourceInfo.packageSource
    } else null
}.getOrNull()
