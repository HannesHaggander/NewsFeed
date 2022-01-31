package com.example.newsreader.ui.newsfeed.states

import androidx.annotation.StringRes
import com.example.newsreader.R
import com.example.newsreader.newsfeed.data.ArticleItemData

sealed class NewsFeedViewState {
    data class Loading(
        @StringRes val loadingMessageRes: Int = R.string.news_feed_loading_message
    ) : NewsFeedViewState()

    data class Success(val result: List<ArticleItemData>) : NewsFeedViewState()

    data class Error(val throwable: Throwable) : NewsFeedViewState()
}
