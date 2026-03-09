package com.rasugames.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasugames.R
import com.rasugames.ui.theme.GameFont
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(onFinished: () -> Unit) {

    val fruits = listOf(
        R.drawable.cherry,
        R.drawable.lemon,
        R.drawable.orange,
        R.drawable.grape,
        R.drawable.bell
    )

    BackHandler(enabled = true) {}

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        val screenWidth = maxWidth
        val spacing = screenWidth / (fruits.size + 1)

        Image(
            painter = painterResource(R.drawable.bg_vertical),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = stringResource(R.string.app_name),
            fontFamily = GameFont,
            textAlign = TextAlign.Center,
            fontSize = 44.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )

        val infiniteTransition = rememberInfiniteTransition(label = "fruits")

        fruits.forEachIndexed { index, fruitRes ->

            val offsetY by infiniteTransition.animateFloat(
                initialValue = -12f,
                targetValue = 12f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200 + index * 200, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "fruit_$index"
            )

            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(index * 200L)
                visible = true
            }

            val scale by animateFloatAsState(
                targetValue = if (visible) 1f else 0f,
                animationSpec = tween(400),
                label = "scale_$index"
            )

            val x = spacing * (index + 1) - screenWidth / 2

            Image(
                painter = painterResource(fruitRes),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = x, y = offsetY.dp)
                    .size(screenWidth * 0.12f) // адаптивный размер
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }

        CircularProgressIndicator(
            color = Color.White,
            strokeWidth = 6.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
                .size(70.dp)
        )
    }
}