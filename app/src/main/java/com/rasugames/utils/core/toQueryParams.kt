package com.rasugames.utils.core

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun Map<String, String?>.toQueryParams(): String {
    return this
        .filterValues { !it.isNullOrEmpty() }
        .map { (key, value) ->
            val encodedKey = URLEncoder.encode(key, StandardCharsets.UTF_8.toString())
            val encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
            "$encodedKey=$encodedValue"
        }
        .joinToString("&")
}
