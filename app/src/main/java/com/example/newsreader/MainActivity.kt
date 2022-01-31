package com.example.newsreader

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsreader.helpers.VIEW_ROUTE_DETAILED_NEWS
import com.example.newsreader.helpers.VIEW_ROUTE_NEWS_FEED
import com.example.newsreader.newsfeed.NewsFeedViewModel
import com.example.newsreader.ui.AppTheme
import com.example.newsreader.ui.Loading
import com.example.newsreader.ui.detailednewsview.DetailedNewsView
import com.example.newsreader.ui.detailednewsview.DetailedNewsViewExtras
import com.example.newsreader.ui.newsfeed.states.NewsFeedError
import com.example.newsreader.ui.newsfeed.states.NewsFeedViewState
import com.example.newsreader.ui.newsfeed.states.PresentArticles
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsFeedViewModel: NewsFeedViewModel by viewModels()

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = VIEW_ROUTE_NEWS_FEED) {
                composable(VIEW_ROUTE_NEWS_FEED) { NewsFeedViewState(navController) }
                composable(VIEW_ROUTE_DETAILED_NEWS) { backStackEntry ->
                    val detailedNewsArgument = backStackEntry
                        .arguments
                        ?.getSerializable(DetailedNewsViewExtras.EXTRAS_KEY)
                        ?.run { this as DetailedNewsViewExtras }
                        ?: throw IllegalArgumentException("Missing DetailedNewsViewExtras in arguments")

                    DetailedNewsView(
                        navController = navController,
                        articleItemData = detailedNewsArgument.articleItemData
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        newsFeedViewModel.updateViewState()
    }

    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }

    @Composable
    fun NewsFeedViewState(navController: NavController) {
        val newsFeed = newsFeedViewModel
            .currentState
            .collectAsState()

        AppTheme {
            when (val state = newsFeed.value) {
                is NewsFeedViewState.Error -> NewsFeedError { newsFeedViewModel.updateViewState() }
                is NewsFeedViewState.Loading -> Loading(loadingText = stringResource(id = R.string.news_feed_loading_message))
                is NewsFeedViewState.Success -> PresentArticles(
                    articles = state.result,
                    navController = navController
                )
            }
        }
    }
}
