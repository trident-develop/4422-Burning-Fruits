package com.rasugames.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.rasugames.ui.theme.GameFont

@Composable
fun LeaderboardScreen(
    prefs: GamePreferences,
    onBack: () -> Unit
) {
    val entries = prefs.getLeaderboard()
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
                    painter = painterResource(R.drawable.leaderboard_tittle),
                    contentDescription = "Leaderboard",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.5f),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (entries.isEmpty()) {
                Text(
                    text = "No scores yet!",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = GameFont,
                    modifier = Modifier.padding(top = 40.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(entries) { index, entry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(0x44FFFFFF),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "#${index + 1}",
                                color = Color(0xFFFFD700),
                                fontSize = 18.sp,
                                fontFamily = GameFont,
                                modifier = Modifier.width(40.dp)
                            )
                            Text(
                                text = "Level ${entry.level}",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = GameFont,
                                modifier = Modifier.weight(1f)
                            )
                            Image(
                                painter = painterResource(R.drawable.star),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${entry.score}",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = GameFont
                            )
                        }
                    }
                }
            }
        }
    }
}