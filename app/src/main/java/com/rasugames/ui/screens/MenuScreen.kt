package com.rasugames.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasugames.R
import com.rasugames.ui.components.MenuButton

@Composable
fun MenuScreen(
    onPlay: () -> Unit,
    onLeaderboard: () -> Unit,
    onHowToPlay: () -> Unit,
    onPrivacyPolicy: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bg_vertical),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Decorative fruits
        Image(
            painter = painterResource(R.drawable.cherry),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 60.dp)
                .size(48.dp)
        )
        Image(
            painter = painterResource(R.drawable.strawberry),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 80.dp)
                .size(48.dp)
        )
        Image(
            painter = painterResource(R.drawable.grape),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 60.dp)
                .size(44.dp)
        )
        Image(
            painter = painterResource(R.drawable.orange),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 80.dp)
                .size(44.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MenuButton(text = "Play", onClick = onPlay)
            MenuButton(text = "Leaderboard", onClick = onLeaderboard, fontSize = 20.sp)
            MenuButton(text = "How To Play", onClick = onHowToPlay, fontSize = 20.sp)
            MenuButton(text = "Privacy Policy", onClick = onPrivacyPolicy, fontSize = 18.sp)
        }
    }
}