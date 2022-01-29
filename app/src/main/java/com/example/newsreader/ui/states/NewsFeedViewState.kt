package com.example.newsreader.ui.states

import androidx.annotation.StringRes
import com.example.newsreader.newsfeed.data.ArticleItemData

sealed class NewsFeedViewState {
    data class Loading(
        @StringRes val loadingMessageRes: Int,
        val progressPercentage: Int? = 0
    ) : NewsFeedViewState()

    data class Success(val result: List<ArticleItemData>) : NewsFeedViewState()

    data class Error(val throwable: Throwable) : NewsFeedViewState()
}
