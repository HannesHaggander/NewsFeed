package com.example.newsreader.ui.newsfeed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsreader.R
import com.example.newsreader.helpers.TEST_TAG_OFFLINE_MODE_INDICATOR
import com.example.newsreader.ui.AppStyle
import com.example.newsreader.ui.AppTheme

@Composable
fun OfflineModeIndicator(
    modifier: Modifier = Modifier,
    onRetryConnection: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.error)
            .clickable { onRetryConnection.invoke() }
            .testTag(TEST_TAG_OFFLINE_MODE_INDICATOR),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.offline_mode_active),
                textAlign = TextAlign.Center,
                style = AppStyle.title,
                color = MaterialTheme.colors.onError
            )
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.offline_mode_active_action),
                textAlign = TextAlign.Center,
                style = AppStyle.body,
                color = MaterialTheme.colors.onError
            )
        }
    }
}

@Preview
@Composable
fun PreviewOfflineModeIndicator() {
    AppTheme {
        OfflineModeIndicator {}
    }
}