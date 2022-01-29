package com.example.newsreader

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsreader.helpers.VIEW_ROUTE_NEWS_FEED
import com.example.newsreader.newsfeed.NewsFeedViewModel
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.Loading
import com.example.newsreader.ui.NewsItemView
import com.example.newsreader.ui.newsfeed.NewsFeedError
import com.example.newsreader.ui.states.NewsFeedViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsFeedViewModel: NewsFeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = VIEW_ROUTE_NEWS_FEED) {
                composable(VIEW_ROUTE_NEWS_FEED) { NewsFeedViewState() }
            }
        }
    }

    @Composable
    fun NewsFeedViewState() {
        val newsFeed = newsFeedViewModel
            .currentState
            .collectAsState()

        when (val state = newsFeed.value) {
            is NewsFeedViewState.Error -> NewsFeedError {
                // todo handle pressed news feed error
            }
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