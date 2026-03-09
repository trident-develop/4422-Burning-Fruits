package com.rasugames.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasugames.R
import com.rasugames.data.GamePreferences
import com.rasugames.ui.components.SquareButton
import com.rasugames.ui.components.peckPress
import com.rasugames.ui.theme.GameFont

@Composable
fun LevelsScreen(
    prefs: GamePreferences,
    onBack: () -> Unit,
    onLevelSelected: (Int) -> Unit
) {
    val maxUnlocked = prefs.maxUnlockedLevel
    BackHandler(enabled = true) { onBack() }
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
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SquareButton(
                    btnRes = R.drawable.back_button,
                    btnMaxWidth = 0.12f,
                    btnClickable = onBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Image(
                    painter = painterResource(R.drawable.levels_tittle),
                    contentDescription = "Levels",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.5f),
                    contentScale = ContentScale.Fit
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                items((1..21).toList()) { level ->
                    val isUnlocked = level <= maxUnlocked
                    LevelItem(
                        level = level,
                        isUnlocked = isUnlocked,
                        onClick = { if (isUnlocked) onLevelSelected(level) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelItem(level: Int, isUnlocked: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .peckPress(onPeck = onClick, isChickenReady = isUnlocked)
    ) {
        Image(
            painter = painterResource(
                if (isUnlocked) R.drawable.level_open_button else R.drawable.level_close_button
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
        if (isUnlocked) {
            Text(
                text = "$level",
                color = Color.White,
                fontSize = 38.sp,
                fontFamily = GameFont
            )
        } else {
            Image(
                painter = painterResource(R.drawable.lock),
                contentDescription = "Locked",
                modifier = Modifier.fillMaxWidth(0.45f),
                contentScale = ContentScale.Fit
            )
        }
    }
}