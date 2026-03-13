package com.rasugames.utils.core

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.rasugames.model.Basd
import com.rasugames.ui.screens.Rasd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.resume

class RouteManager(val activity: ComponentActivity) {
    var oneView: Basd = Basd(activity, Rasd(activity))
    lateinit var ref: String
    var status: String

    init {
        try {
            status =
        Settings.Global.getString(activity.contentResolver, Settings.Global.ADB_ENABLED) ?: "0"
//                "0"
        } catch (e: Exception) {
            status = "1"
        }
    }

    fun getOneV(): Basd {
        return oneView
    }

    suspend fun getR() {
        withContext(Dispatchers.IO) {
            ref = getRef(activity)
        }
    }

    private suspend fun getRef(context: Context): String =
        suspendCancellableCoroutine { continuation ->
            val client = InstallReferrerClient.newBuilder(context).build()
            val isResumed = AtomicBoolean(false)

            continuation.invokeOnCancellation {
                client.endConnection()
            }

            client.startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    try {
                        if (!isResumed.compareAndSet(false, true)) return

                        val result =
                            if (responseCode == InstallReferrerClient.InstallReferrerResponse.OK) {
                                client.installReferrer.installReferrer
                            } else {
                                "null"
                            }

                continuation.resume(result)
//                        continuation.resume("cmpgn=aaaa_TEST-Deeplink_bbbb_cccc_dddd")
//                continuation.resume("cmpgn=aaaa_MA-TEST_bbbb_cccc_dddd")

                    } catch (_: Exception) {
                        if (isResumed.compareAndSet(false, true)) {
                            continuation.resume("null")
                        }
                    } finally {
                        client.endConnection()
                    }
                }

                override fun onInstallReferrerServiceDisconnected() {
                    if (isResumed.compareAndSet(false, true)) {
                        continuation.resume("null")
                    }
                }
            })
        }

    fun getTime(): String {
        val packageInfo: PackageInfo =
            activity.packageManager.getPackageInfo(activity.packageName, 0)
        val time = packageInfo.firstInstallTime.toString()
        return time
    }

    fun getDev(): String {
        val device =
            Build.BRAND.replaceFirstChar { it.titlecase(Locale.getDefault()) } + " " + Build.MODEL
        val encoded2 = URLEncoder.encode(device, StandardCharsets.UTF_8.toString())
        return encoded2
    }
}
