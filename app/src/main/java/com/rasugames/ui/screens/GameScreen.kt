package com.rasugames.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasugames.R
import com.rasugames.data.GamePreferences
import com.rasugames.game.LevelGenerator
import com.rasugames.model.Cell
import com.rasugames.model.CellContent
import com.rasugames.model.LevelConfigs
import com.rasugames.ui.components.SquareButton
import com.rasugames.ui.components.peckPress
import com.rasugames.ui.theme.GameFont

private enum class GameState { PLAYING, PAUSED, WON, LOST }

@Composable
fun GameScreen(
    level: Int,
    prefs: GamePreferences,
    onHome: () -> Unit,
    onBack: () -> Unit,
    onNextLevel: (Int) -> Unit
) {
    val config = LevelConfigs.getConfig(level)
    var gameState by remember(level) { mutableStateOf(GameState.PLAYING) }
    var score by remember(level) { mutableIntStateOf(0) }
    var hearts by remember(level) { mutableIntStateOf(3) }
    var multiplier by remember(level) { mutableIntStateOf(1) }
    val cells = remember(level) { mutableStateListOf(*LevelGenerator.generate(config).toTypedArray()) }
    BackHandler(enabled = true) { onBack() }
    fun restart() {
        gameState = GameState.PLAYING
        score = 0
        hearts = 3
        multiplier = 1
        cells.clear()
        cells.addAll(LevelGenerator.generate(config))
    }

    fun onCellTap(index: Int) {
        if (gameState != GameState.PLAYING) return
        val cell = cells[index]
        if (cell.revealed) return

        cells[index] = cell.copy(revealed = true)

        when (cell.content) {
            CellContent.CHERRY, CellContent.LEMON, CellContent.ORANGE,
            CellContent.GRAPE, CellContent.STRAWBERRY -> {
                score += cell.content.points
            }
            CellContent.DIAMOND -> {
                score += cell.content.points
            }
            CellContent.TRAP -> {
                hearts--
                if (hearts <= 0) {
                    gameState = GameState.LOST
                }
            }
            CellContent.CHEST -> {
                val multipliers = listOf(5, 15, 33)
                multiplier = multipliers.random()
                score = (score + 50) * multiplier
                prefs.unlockNextLevel(level)
                prefs.saveBestScore(level, score)
                prefs.addLeaderboardEntry(level, score)
                gameState = GameState.WON
            }
            CellContent.EMPTY -> { /* nothing */ }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bg_vertical),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SquareButton(
                    btnRes = R.drawable.back_button,
                    btnMaxWidth = 0.11f,
                    btnClickable = onBack
                )

                // Level label
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.score_bg),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    Text(
                        text = "Level $level",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = GameFont
                    )
                }

                SquareButton(
                    btnRes = R.drawable.replay_button,
                    btnMaxWidth = 0.13f,
                    btnClickable = { restart() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Score and hearts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(3) { i ->
                        Image(
                            painter = painterResource(R.drawable.heart),
                            contentDescription = "Heart",
                            modifier = Modifier.size(28.dp),
                            alpha = if (i < hearts) 1f else 0.3f
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.star),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$score",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = GameFont
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Game grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(config.cols),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(cells) { index, cell ->
                    GameCell(
                        cell = cell,
                        enabled = gameState == GameState.PLAYING,
                        onClick = { onCellTap(index) }
                    )
                }
            }
        }

        // Overlays
        when (gameState) {
            GameState.PAUSED -> PauseOverlay(
                onResume = { gameState = GameState.PLAYING },
                onReplay = { restart() },
                onHome = onHome
            )
            GameState.WON -> WinOverlay(
                score = score,
                multiplier = multiplier,
                isLastLevel = level >= 21,
                onReplay = { restart() },
                onHome = onHome,
                onNextLevel = { onNextLevel(level + 1) }
            )
            GameState.LOST -> LoseOverlay(
                onReplay = { restart() },
                onHome = onHome
            )
            GameState.PLAYING -> { /* no overlay */ }
        }
    }
}

@Composable
private fun GameCell(cell: Cell, enabled: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .then(
                if (!cell.revealed && enabled) {
                    Modifier.peckPress(onPeck = onClick, isChickenReady = true)
                } else {
                    Modifier
                }
            )
    ) {
        if (!cell.revealed) {
            // Closed cell
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFF9800), Color(0xFFE65100))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(2.dp, Color(0xFFFFCC02), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "?",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontFamily = GameFont
                )
            }
        } else {
            // Revealed cell
            val bgColor = when (cell.content) {
                CellContent.TRAP -> Color(0x44FF0000)
                CellContent.CHEST -> Color(0x44FFD700)
                CellContent.EMPTY -> Color(0x22FFFFFF)
                else -> Color(0x33FFFFFF)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgColor, RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0x44FFFFFF), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = true,
                    enter = scaleIn(tween(300)) + fadeIn(tween(300))
                ) {
                    val drawableRes = cell.content.drawableRes
                    if (drawableRes != null) {
                        Image(
                            painter = painterResource(drawableRes),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(0.7f)
                                .aspectRatio(1f)
                        )
                    } else if (cell.content == CellContent.TRAP) {
                        Text(
                            text = "💥",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}