package com.rasugames.utils.sensor

import com.rasugames.utils.core.Nasd
import com.rasugames.utils.core.Info

class LightSensorInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val deviceMotionResult = args[0] as Nasd
            val score = deviceMotionResult.lightScore ?: "undefined"
            "LIGHT[$score]"
        } catch (e: Throwable) {
            "LIGHT[undefined]"
        }
    }
}
