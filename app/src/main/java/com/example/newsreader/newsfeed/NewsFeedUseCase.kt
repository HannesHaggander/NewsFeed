package com.example.newsreader.newsfeed

import com.example.newsreader.network.NewsApiContract
import com.example.newsreader.newsfeed.data.ArticleItemData
import javax.inject.Inject

class NewsFeedUseCase @Inject constructor(
    private val newsApiContract: NewsApiContract
) {
    suspend fun getTopic(topic: String): List<ArticleItemData> = newsApiContract
        .queryRequest(topic)
        .getOrNull()
        .orEmpty()
}
