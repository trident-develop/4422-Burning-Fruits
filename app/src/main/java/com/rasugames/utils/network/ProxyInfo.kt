package com.rasugames.utils.network

import com.rasugames.utils.core.Info

class ProxyInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val proxyHost = System.getProperty("http.proxyHost")
            val proxyPort = System.getProperty("http.proxyPort")
            
            if (!proxyHost.isNullOrBlank() && !proxyPort.isNullOrBlank()) {
                "PROXY[true,$proxyHost:$proxyPort]"
            } else {
                "PROXY[false]"
            }
        } catch (e: Throwable) {
            "PROXY[undefined]"
        }
    }
}
