package com.example.newsreader.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.R
import com.example.newsreader.ViewState
import com.example.newsreader.helpers.safeEmit
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedUseCase: NewsFeedUseCase
) : ViewModel() {

    val currentState = flow {
        newsFeed.collectLatest { articles ->
            if (articles.isEmpty()) {
                safeEmit(ViewState.Error(Throwable("error")))
            } else {
                safeEmit(ViewState.Success(articles))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ViewState.Loading(R.string.news_feed_loading_message)
    )

    val newsFeed = flow {
        newsFeedUseCase
            .getRecommendedTopics()
            .let { articles -> safeEmit(articles) }
    }

}