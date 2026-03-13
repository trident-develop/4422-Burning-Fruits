package com.rasugames.model

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun asfafv(context: Context): String = withContext(Dispatchers.IO) {
    try {
        val info = AdvertisingIdClient.getAdvertisingIdInfo(context)
        if (!info.isLimitAdTrackingEnabled) {
            info.id ?: "00000000-0000-0000-0000-000000000000"
        } else {
            "00000000-0000-0000-0000-000000000000"
        }
    } catch (_: Exception) {
        "00000000-0000-0000-0000-000000000000"
    }
}
