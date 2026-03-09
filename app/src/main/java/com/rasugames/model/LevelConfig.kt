package com.rasugames.model

data class LevelConfig(
    val level: Int,
    val rows: Int,
    val cols: Int,
    val trapCount: Int,
    val diamondCount: Int,
    val emptyCount: Int
) {
    val totalCells: Int get() = rows * cols
}

object LevelConfigs {
    fun getConfig(level: Int): LevelConfig = when (level) {
        1  -> LevelConfig(1,  4, 4, trapCount = 1, diamondCount = 1, emptyCount = 2)
        2  -> LevelConfig(2,  4, 4, trapCount = 2, diamondCount = 1, emptyCount = 2)
        3  -> LevelConfig(3,  4, 4, trapCount = 2, diamondCount = 1, emptyCount = 3)
        4  -> LevelConfig(4,  4, 4, trapCount = 3, diamondCount = 1, emptyCount = 2)
        5  -> LevelConfig(5,  4, 4, trapCount = 3, diamondCount = 2, emptyCount = 2)
        6  -> LevelConfig(6,  5, 4, trapCount = 3, diamondCount = 1, emptyCount = 3)
        7  -> LevelConfig(7,  5, 4, trapCount = 4, diamondCount = 1, emptyCount = 3)
        8  -> LevelConfig(8,  5, 4, trapCount = 4, diamondCount = 2, emptyCount = 4)
        9  -> LevelConfig(9,  5, 5, trapCount = 4, diamondCount = 1, emptyCount = 4)
        10 -> LevelConfig(10, 5, 5, trapCount = 5, diamondCount = 2, emptyCount = 4)
        11 -> LevelConfig(11, 5, 5, trapCount = 5, diamondCount = 1, emptyCount = 5)
        12 -> LevelConfig(12, 5, 5, trapCount = 6, diamondCount = 1, emptyCount = 5)
        13 -> LevelConfig(13, 5, 5, trapCount = 6, diamondCount = 2, emptyCount = 5)
        14 -> LevelConfig(14, 5, 5, trapCount = 7, diamondCount = 1, emptyCount = 6)
        15 -> LevelConfig(15, 5, 5, trapCount = 7, diamondCount = 2, emptyCount = 5)
        16 -> LevelConfig(16, 6, 5, trapCount = 7, diamondCount = 2, emptyCount = 6)
        17 -> LevelConfig(17, 6, 5, trapCount = 8, diamondCount = 2, emptyCount = 6)
        18 -> LevelConfig(18, 6, 5, trapCount = 8, diamondCount = 2, emptyCount = 7)
        19 -> LevelConfig(19, 6, 5, trapCount = 9, diamondCount = 2, emptyCount = 7)
        20 -> LevelConfig(20, 6, 6, trapCount = 9, diamondCount = 2, emptyCount = 8)
        21 -> LevelConfig(21, 6, 6, trapCount = 10, diamondCount = 3, emptyCount = 8)
        else -> LevelConfig(level, 4, 4, trapCount = 2, diamondCount = 1, emptyCount = 2)
    }
}
