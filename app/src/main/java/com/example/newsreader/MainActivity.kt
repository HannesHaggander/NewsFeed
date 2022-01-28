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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsreader.newsfeed.NewsFeedViewModel
import com.kwabenaberko.newsapilib.models.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsFeedViewModel: NewsFeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(text = "test")
//            val navController = rememberNavController()
//            NavHost(navController = navController, startDestination = "hello") {
//                composable("hello") { HelloCompose() }
//            }
        }
    }

    @Composable
    fun HelloCompose(){
//        val newsFeed = newsFeedViewModel
//            .newsFeed
//            .collectAsState(initial = listOf())
//
//        Column {
//            newsFeed.value.forEach { article ->
//                Text(text = article.title)
//                Text(text = article.description)
//            }
//        }
    }

}