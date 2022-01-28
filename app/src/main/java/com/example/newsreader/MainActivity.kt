package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsreader.newsfeed.NewsFeedViewModel
import com.example.newsreader.ui.Loading
import com.kwabenaberko.newsapilib.models.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsFeedViewModel: NewsFeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "hello") {
                composable("hello") {
                    HelloCompose() }
            }
        }
    }

    @Composable
    fun HelloCompose(){
        val newsFeed = newsFeedViewModel
            .currentState
            .collectAsState()

        when(val state = newsFeed.value){
            is ViewState.Error -> Text(text = "error")
            is ViewState.Loading -> Loading(loadingText = stringResource(id = R.string.news_feed_loading_message))
            is ViewState.Success<*> -> {/* TODO */}
        }
    }

    @Composable
    fun ShowArticles(articles: List<Article>){

    }

}