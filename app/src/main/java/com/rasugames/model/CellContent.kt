package com.rasugames.model

import com.rasugames.R

enum class CellContent(val points: Int, val drawableRes: Int?) {
    CHERRY(10, R.drawable.cherry),
    LEMON(15, R.drawable.lemon),
    ORANGE(20, R.drawable.orange),
    GRAPE(25, R.drawable.grape),
    STRAWBERRY(30, R.drawable.strawberry),
    DIAMOND(100, R.drawable.diamond),
    CHEST(0, R.drawable.chest),
    TRAP(0, null),
    EMPTY(0, null);
}

data class Cell(
    val content: CellContent,
    val revealed: Boolean = false
)
