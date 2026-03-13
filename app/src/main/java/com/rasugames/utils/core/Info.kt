package com.rasugames.utils.core

fun interface Info {
    suspend fun collect(vararg args: Any?): String
}