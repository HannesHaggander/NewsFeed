package com.example.newsreader.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.R
import com.example.newsreader.helpers.safeEmit
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.states.NewsFeedViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedUseCase: NewsFeedUseCase
) : ViewModel() {

    val currentState = flow {
        getPopularCategories().let { articles ->
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

    private suspend fun getPopularCategories(): List<ArticleItemData> = suspendCoroutine { s ->
        viewModelScope.launch(Dispatchers.IO) {
            RECOMMENDED_TOPICS
                .map { topic -> async { newsFeedUseCase.getTopic(topic) } } // retrieve in parallel
                .awaitAll()
                .flatten()
                .let { result ->
                    withContext(Dispatchers.Main) {
                        s.resume(result)
                    }
                }
        }
    }

    companion object {
        val RECOMMENDED_TOPICS = listOf(
            "Apple",
            "Google",
            "Facebook", // update to new company name "meta"?
        )
    }
}