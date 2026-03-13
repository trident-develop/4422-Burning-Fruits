package com.rasugames.utils.sensor

import com.rasugames.utils.core.Info
import com.rasugames.utils.core.Nasd

class MagnetometerInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val deviceMotionResult = args[0] as Nasd
            val score = deviceMotionResult.magScore ?: "undefined"
            "MAGN[$score]"
        } catch (e: Throwable) {
            "MAGN[undefined]"
        }
    }
}
