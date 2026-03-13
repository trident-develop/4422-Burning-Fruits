package com.rasugames.game

import android.content.Context
import android.webkit.WebSettings
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.rasugames.data.Mda
import com.rasugames.model.kfa
import com.rasugames.navigation.NavigationStore.navigate
import com.rasugames.navigation.ScreenNav
import com.rasugames.ui.components.DomHand
import com.rasugames.utils.core.RouteManager
import com.rasugames.utils.core.getAll
import com.rasugames.utils.core.toQueryParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

suspend fun onEx(
    context: Context,
    startD: RouteManager,
) {
    try {
        var firebaseId = ""

        firebaseId = try {
            Firebase.analytics.appInstanceId.await().toString()
        } catch (e: Exception) {
            ""
        }

        withContext(Dispatchers.Main) {
            val wv = startD.oneView
            val u = "${DomHand().long}?" + kfa(
                startD.ref, firebaseId, context, startD
            )
            val map = getAll(context)
            val updU = u + "&" + map.toQueryParams()
            if (updU.isNotEmpty()) {
                try {
                    val assdf = WebSettings.getDefaultUserAgent(context)
                    wv.loadUrl(updU, mapOf(Mda.cua to assdf))
                } catch (e: Exception) {
                    navigate(ScreenNav.Move)
                }
            } else {
                navigate(ScreenNav.Move)
            }
        }
    } catch (e: Exception) {
        navigate(ScreenNav.Move)
    }
}
