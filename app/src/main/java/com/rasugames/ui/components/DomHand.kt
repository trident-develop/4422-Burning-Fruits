package com.rasugames.ui.components

import com.rasugames.data.Mda


class DomHand {
    val scheme = "https"
    private val escape = "://."
    private val baseDomain = Mda.handHelp
    private val index = Mda.indexNum

    val dom: String by lazy {
        baseDomain.substring(0, index) +
                escape.last() +
                baseDomain.substring(index)
    }

    private val randomEscapeChar: Char by lazy {
        escape.dropLast(1).drop(1).random()
    }

    val short: String by lazy {
        "$scheme${escape.dropLast(1)}$dom$randomEscapeChar"
    }

    val long: String by lazy {
        "${short}privacypolicy$randomEscapeChar"
    }
}
