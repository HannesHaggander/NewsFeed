package com.example.newsreader.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.helpers.safeEmit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedUseCase: NewsFeedUseCase
) : ViewModel() {

    val newsFeed = flow {
        newsFeedUseCase
            .getRecommendedTopics()
            .let { articles -> safeEmit(articles) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

}