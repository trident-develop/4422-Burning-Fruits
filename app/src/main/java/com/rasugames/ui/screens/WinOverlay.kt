package com.rasugames.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasugames.R
import com.rasugames.ui.components.SquareButton
import com.rasugames.ui.components.peckPress
import com.rasugames.ui.theme.GameFont

@Composable
fun WinOverlay(
    score: Int,
    multiplier: Int,
    isLastLevel: Boolean,
    onReplay: () -> Unit,
    onHome: () -> Unit,
    onNextLevel: () -> Unit
) {
    var animStarted by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animStarted = true }

    val scale by animateFloatAsState(
        targetValue = if (animStarted) 1f else 0.3f,
        animationSpec = tween(500),
        label = "winScale"
    )

    val multiplierRes = when (multiplier) {
        5 -> R.drawable.x5_19
        15 -> R.drawable.x15_2
        33 -> R.drawable.x33_9
        else -> null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xCC000000)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        ) {
            Image(
                painter = painterResource(R.drawable.big_win),
                contentDescription = "You Win!",
                modifier = Modifier.fillMaxWidth(0.7f),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (multiplierRes != null) {
                Image(
                    painter = painterResource(multiplierRes),
                    contentDescription = "x$multiplier",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$score",
                    color = Color(0xFFFFD700),
                    fontSize = 32.sp,
                    fontFamily = GameFont
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SquareButton(
                    btnRes = R.drawable.replay_button,
                    btnMaxWidth = 0.16f,
                    btnClickable = onReplay
                )
                Spacer(modifier = Modifier.width(16.dp))
                SquareButton(
                    btnRes = R.drawable.home_button,
                    btnMaxWidth = 0.16f,
                    btnClickable = onHome
                )
                if (!isLastLevel) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(R.drawable.next_level_button),
                        contentDescription = "Next Level",
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .peckPress(onPeck = onNextLevel, isChickenReady = true)
                    )
                }
            }
        }
    }
}
