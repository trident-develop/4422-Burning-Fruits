package com.rasugames.game

import com.rasugames.model.Cell
import com.rasugames.model.CellContent
import com.rasugames.model.LevelConfig
import kotlin.random.Random

object LevelGenerator {

    private val fruits = listOf(
        CellContent.CHERRY,
        CellContent.LEMON,
        CellContent.ORANGE,
        CellContent.GRAPE,
        CellContent.STRAWBERRY
    )

    fun generate(config: LevelConfig): List<Cell> {
        val random = Random(config.level * 7919L)
        val total = config.totalCells
        val contents = mutableListOf<CellContent>()

        contents.add(CellContent.CHEST)
        repeat(config.trapCount) { contents.add(CellContent.TRAP) }
        repeat(config.diamondCount) { contents.add(CellContent.DIAMOND) }
        repeat(config.emptyCount) { contents.add(CellContent.EMPTY) }

        val remaining = total - contents.size
        repeat(remaining) {
            contents.add(fruits[random.nextInt(fruits.size)])
        }

        contents.shuffle(random)
        return contents.map { Cell(it) }
    }
}
