package com.example.newsreader.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.newsfeed.states.NewsFeedViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class NewsFeedViewModel @Inject constructor(
    private val newsFeedUseCase: NewsFeedUseCase
) : ViewModel() {

    val currentState = flow {
        emit(NewsFeedViewState.Loading())
        getTopics().let { articles ->
            if (articles.isEmpty()) {
                emit(NewsFeedViewState.Error(Throwable("Failed to find any articles")))
            } else {
                emit(NewsFeedViewState.Success(articles))
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, NewsFeedViewState.Loading())

    private suspend fun getTopics() = suspendCoroutine<List<ArticleItemData>> { suspend ->
        viewModelScope.launch(Dispatchers.IO) {
            RECOMMENDED_TOPICS
                .map { async { newsFeedUseCase.getTopic(it) } } // fetch in parallel
                .awaitAll()
                .flatten()
                .let { articles -> suspend.resume(articles) }
        }
    }

    fun updateViewState() {
        viewModelScope.launch {
            currentState.collectLatest { /* Collect data */ }
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
