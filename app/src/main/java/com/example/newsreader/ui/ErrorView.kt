package com.example.newsreader.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
        modifier = modifier
            .background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = AppStyle.title,
            color = MaterialTheme.colors.error,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(text = description, style = AppStyle.body, color = MaterialTheme.colors.onSurface)
        action?.let { click ->
            Button(
                onClick = click,
                modifier = modifier
                    .padding(8.dp)
                    .wrapContentWidth()
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary,
                    disabledBackgroundColor = MaterialTheme.colors.background,
                    disabledContentColor = MaterialTheme.colors.onBackground
                )
            ) {
                Text(
                    text = actionText,
                    style = AppStyle.utility,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
fun ErrorViewPreview() {
    AppTheme {
        ErrorView(
            title = "Error occurred",
            description = "This is a preview error",
            actionText = "Reload",
            action = {}
        )
    }
}
