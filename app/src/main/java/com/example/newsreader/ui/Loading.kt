package com.example.newsreader.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Loading(
    modifier: Modifier = Modifier.fillMaxSize(),
    loadingText: String
){
    Text(
        modifier = modifier,
        text = loadingText,
        textAlign = TextAlign.Center,
    )
}