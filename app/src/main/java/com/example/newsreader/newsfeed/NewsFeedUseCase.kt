package com.example.newsreader.newsfeed

import com.example.newsreader.network.NewsApiProvider
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import javax.inject.Inject

class NewsFeedUseCase @Inject constructor(
    private val newsApiProvider: NewsApiProvider
) {

    suspend fun getRecommendedTopics(): List<Article> = RECOMMENDED_TOPICS
        .mapNotNull { topic -> newsApiProvider.getCategory(topic).getOrNull() }
        .flatMap { article -> article.articles }

    companion object {
        private val RECOMMENDED_TOPICS = listOf(
            "Apple",
            "Google",
            "Facebook", // update to new company name "meta"?
        )
    }

}