package com.rasugames.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.rasugames.R
import com.rasugames.ui.components.SquareButton
import com.rasugames.ui.theme.GameFont

@Composable
fun HowToPlayScreen(onBack: () -> Unit) {
    val isInPreview = LocalInspectionMode.current
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
                    painter = painterResource(R.drawable.how_to_play_tittle),
                    contentDescription = "How To Play",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.5f),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 30.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                RuleItem(R.drawable.cherry, "Tap closed cells to reveal what's hidden inside. Collect fruits to earn points!")
                RuleItem(R.drawable.heart, "You have 3 hearts. Trap cells take away a heart. Lose all hearts and it's game over!")
                RuleItem(R.drawable.chest, "Find the treasure chest to complete the level and earn a huge bonus!")
                RuleItem(R.drawable.diamond, "Diamonds give you 100 bonus points. Look for them!")
                RuleItem(R.drawable.x5_19, "When you find the chest, you get a random multiplier: x5, x15, or x33!")
                RuleItem(R.drawable.star, "Complete levels to unlock the next one. Try to beat your high score!")
            }
        }

        if (!isInPreview) {
            AndroidView(
                factory = {
                    val adView = AdView(it)
                    adView.setAdSize(AdSize.BANNER)
                    adView.adUnitId = "ca-app-pub-3940256099942544/9214589741"
                    adView.loadAd(AdRequest.Builder().build())
                    adView
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun RuleItem(iconRes: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = GameFont,
            lineHeight = 20.sp
        )
    }
}