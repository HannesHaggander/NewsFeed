package com.example.newsreader.newsfeed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.R
import com.example.newsreader.helpers.safeEmit
import com.example.newsreader.ui.states.NewsFeedViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedUseCase: NewsFeedUseCase
) : ViewModel() {

    val currentState = flow {
        newsFeed.collectLatest { articles ->
            if (articles.isEmpty()) {
                safeEmit(NewsFeedViewState.Error(Throwable("Failed to find any articles")))
            } else {
                safeEmit(NewsFeedViewState.Success(articles))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        NewsFeedViewState.Loading(R.string.news_feed_loading_message)
    )

    val newsFeed = flow {
        newsFeedUseCase
            .getRecommendedTopics()
            .let { articles -> safeEmit(articles) }
    }
}