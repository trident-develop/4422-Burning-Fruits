package com.rasugames.utils.sensor

import com.rasugames.utils.core.Info
import com.rasugames.utils.core.Nasd

class AccelerometerInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val deviceMotionResult = args[0] as Nasd
            val score = deviceMotionResult.accelScore ?: "undefined"
            "ACC[$score]"
        } catch (e: Throwable) {
            "ACC[undefined]"
        }
    }
}