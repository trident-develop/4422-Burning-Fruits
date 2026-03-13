package com.rasugames.model

import android.content.Context
import com.rasugames.data.Mda
import com.rasugames.ui.screens.ps
import com.rasugames.utils.core.RouteManager
import java.net.URLEncoder

suspend fun kfa(raw: String, id: String, context: Context, startD: RouteManager): String {
    val encoded = URLEncoder.encode(raw, "UTF-8")
    val ps = ps(context)
    val last =
        "&${Mda.ep1}=${startD.getTime()}&${Mda.ep2}=${startD.status}&${Mda.ep3}=${startD.getDev()}&${Mda.ep10}=$ps"
    asfafv(context).let {
        return "${Mda.gk}=${it}&${Mda.rk}=$encoded&${Mda.ei}=$id" + last
    }
}
