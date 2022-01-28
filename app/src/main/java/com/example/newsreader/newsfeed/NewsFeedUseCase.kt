package com.example.newsreader.newsfeed

import com.example.newsreader.network.NewsApiProvider
import javax.inject.Inject

class NewsFeedUseCase @Inject constructor(
    private val newsApiProvider: NewsApiProvider
) {

}