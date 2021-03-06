package com.example.newsreader.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.helpers.onEmpty
import com.example.newsreader.helpers.onPopulated
import com.example.newsreader.helpers.safeEmit
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.newsfeed.states.NewsFeedViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class NewsFeedViewModel @Inject constructor(
    private val newsFeedUseCase: NewsFeedUseCase
) : ViewModel() {

    private val _currentStateFlow =
        MutableStateFlow<NewsFeedViewState>(value = NewsFeedViewState.Loading())
    val currentStateFlow: StateFlow<NewsFeedViewState>
        get() = _currentStateFlow

    fun updateCurrentViewState() {
        viewModelScope.launch {
            _currentStateFlow.safeEmit(NewsFeedViewState.Loading())
            getRecommendedTopicsFromNetwork()
                .onEmpty { tryOfflineMode() }
                .onPopulated { values ->
                    val successState = NewsFeedViewState.Success(values, isOffline = false)
                    _currentStateFlow.safeEmit(successState)
                }
        }
    }

    private suspend fun tryOfflineMode() {
        attemptToFetchStoredArticles()
            .onEmpty {
                val errorState = NewsFeedViewState.Error(Throwable("Failed to find articles"))
                _currentStateFlow.safeEmit(errorState)
            }
            .onPopulated { values ->
                _currentStateFlow.safeEmit(NewsFeedViewState.Success(values, isOffline = true))
            }
    }

    /**
     * Context swapping function for retrieving database values as not to violate flow rules
     */
    private suspend fun attemptToFetchStoredArticles(): List<ArticleItemData> =
        newsFeedUseCase.run {
            withContext(Dispatchers.IO) {
                return@withContext getStoredArticlesFromDatabase().let { databaseArticles ->
                    withContext(Dispatchers.Main) {
                        return@withContext databaseArticles
                    }
                }
            }
        }

    private suspend fun getRecommendedTopicsFromNetwork() =
        suspendCoroutine<List<ArticleItemData>> { suspend ->
            viewModelScope.launch(Dispatchers.IO) {
                RECOMMENDED_TOPICS
                    .map { async { newsFeedUseCase.getTopic(it) } } // fetch in parallel
                    .awaitAll()
                    .flatten()
                    .let { articles -> suspend.resume(articles) }
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
