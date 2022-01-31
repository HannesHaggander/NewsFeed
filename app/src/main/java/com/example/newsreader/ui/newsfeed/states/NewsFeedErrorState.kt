package com.example.newsreader.ui.newsfeed.states

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.newsreader.R
import com.example.newsreader.ui.ErrorView

@Composable
fun NewsFeedError(onRetry: () -> Unit) {
    ErrorView(
        title = stringResource(id = R.string.news_feed_loading_error_title),
        description = stringResource(id = R.string.news_feed_loading_error_description),
        actionText = stringResource(id = R.string.news_feed_loading_error_action),
        action = onRetry
    )
}
