package com.example.newsreader.newsfeed

import androidx.lifecycle.ViewModel
import com.example.newsreader.network.NewsApiProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor (
    private val newsApiProvider: NewsApiProvider
): ViewModel() {

}