package com.example.newsreader.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    title: String,
    description: String,
    actionText: String,
    action: (() -> Unit)?,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title)
        Text(text = description)
        action?.let { click ->
            Button(
                onClick = click,
                modifier = modifier
                    .padding(8.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Text(text = actionText)
            }
        }
    }
}

@Preview
@Composable
fun ErrorViewPreview() {
    ErrorView(
        title = "Error occurred",
        description = "This is a preview error",
        actionText = "Reload",
        action = {}
    )
}
