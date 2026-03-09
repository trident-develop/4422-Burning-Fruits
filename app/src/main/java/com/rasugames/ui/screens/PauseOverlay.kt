package com.rasugames.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rasugames.R
import com.rasugames.ui.components.MenuButton
import com.rasugames.ui.components.SquareButton

@Composable
fun PauseOverlay(
    onResume: () -> Unit,
    onReplay: () -> Unit,
    onHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xCC000000)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.pause_tittle),
                contentDescription = "Paused",
                modifier = Modifier.fillMaxWidth(0.5f),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            MenuButton(text = "Resume", onClick = onResume)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SquareButton(
                    btnRes = R.drawable.replay_button,
                    btnMaxWidth = 0.16f,
                    btnClickable = onReplay
                )
                Spacer(modifier = Modifier.width(24.dp))
                SquareButton(
                    btnRes = R.drawable.home_button,
                    btnMaxWidth = 0.16f,
                    btnClickable = onHome
                )
            }
        }
    }
}
