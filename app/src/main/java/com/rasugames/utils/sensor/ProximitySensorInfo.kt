package com.rasugames.utils.sensor

import com.rasugames.utils.core.Nasd
import com.rasugames.utils.core.Info

class ProximitySensorInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val deviceMotionResult = args[0] as Nasd
            val score = deviceMotionResult.proxScore ?: "undefined"
            "PROXIMITY[$score]"
        } catch (e: Throwable) {
            "PROXIMITY[undefined]"
        }
    }
}
