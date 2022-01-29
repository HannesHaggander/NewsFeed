package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsreader.newsfeed.NewsFeedViewModel
import com.example.newsreader.ui.Loading
import com.example.newsreader.ui.states.NewsFeedViewState
import com.kwabenaberko.newsapilib.models.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.NewsItemView

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsFeedViewModel: NewsFeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "hello") {
                composable("hello") {
                    HelloCompose()
                }
            }
        }

    }

    @Composable
    fun HelloCompose() {
        val newsFeed = newsFeedViewModel
            .currentState
            .collectAsState()

        when (val state = newsFeed.value) {
            is NewsFeedViewState.Error -> Text(text = state.throwable.localizedMessage.orEmpty())
            is NewsFeedViewState.Loading -> Loading(loadingText = stringResource(id = R.string.news_feed_loading_message))
            is NewsFeedViewState.Success -> ShowArticles(articles = state.result)
        }
    }

    @Composable
    fun ShowArticles(articles: List<ArticleItemData>) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState,
                    enabled = true,
                )
        ) {
            articles.forEach { item -> NewsItemView(articleItemData = item) }
        }
    }

}