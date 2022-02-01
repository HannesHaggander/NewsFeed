package com.example.newsreader.ui

import androidx.cardview.widget.CardView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.newsreader.R

@Composable
fun Loading(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
    loadingText: String
) {
    val lottieComposition = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.splashy_loading)
    )

    val lottieProgress by animateLottieCompositionAsState(
        composition = lottieComposition.value,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = lottieComposition.value,
            progress = lottieProgress,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.25f)
                .padding(8.dp)
        )

        Text(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentHeight(),
            text = loadingText,
            textAlign = TextAlign.Center,
            style = AppStyle.title,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Preview
@Composable
fun LoadingPreview() {
    AppTheme {
        Loading(loadingText = "Loading")
    }
}