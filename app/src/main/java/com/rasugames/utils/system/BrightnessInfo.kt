package com.rasugames.utils.system

import android.content.Context
import android.content.res.Resources
import android.provider.Settings
import com.rasugames.utils.core.Info

class BrightnessInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val context = args[0] as Context
            val currentBrightness = Settings.System.getInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS
            )
            val maxBrightness = getSystemMaxBrightness()
            val percentage = (currentBrightness / maxBrightness * 100).toInt()
            "BRIGHT[${percentage.coerceIn(0, 100)}]"
        } catch (_: Throwable) {
            "BRIGHT[undefined]"
        }
    }

    private fun getSystemMaxBrightness(): Float {
        return try {
            val res = Resources.getSystem()
            val resId = res.getIdentifier("config_screenBrightnessSettingMaximum", "integer", "android")
            if (resId != 0) res.getInteger(resId).toFloat() else 255f
        } catch (_: Throwable) {
            255f
        }
    }
}
