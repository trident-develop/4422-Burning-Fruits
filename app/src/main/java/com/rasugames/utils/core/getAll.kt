package com.rasugames.utils.core

import android.content.Context
import com.rasugames.data.Mda

suspend fun getAll(context: Context) : Map<String, String> {
        val dev = DevicePropertiesResult.create(context)

        val map = mutableMapOf<String, String>()

        map[Mda.ep4] = dev.getX4()
        map[Mda.ep5] = dev.getX5()
        map[Mda.ep8] = dev.getX8()
        map[Mda.ep9] = dev.getX9()
        map[Mda.ep11] = dev.getS28()
        map[Mda.ep12] = dev.getS30()

        return map
}
