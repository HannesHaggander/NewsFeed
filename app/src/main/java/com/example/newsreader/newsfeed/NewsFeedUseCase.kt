package com.example.newsreader.newsfeed

import com.example.newsreader.network.NewsApiProvider
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.kwabenaberko.newsapilib.models.Article
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class NewsFeedUseCase @Inject constructor(
    private val newsApiProvider: NewsApiProvider
) {

    suspend fun getRecommendedTopics(): List<ArticleItemData> = RECOMMENDED_TOPICS
        .mapNotNull { topic -> newsApiProvider.getCategory(topic).getOrNull() }
        .flatMap { article -> article.articles }
        .map { article -> article.toDomainModel() }

    private fun Article.toDomainModel(): ArticleItemData = ArticleItemData(
        id = UUID.randomUUID(),
        url = url,
        title = title,
        description = description,
        publishedAt = publishedAt,
        urlToImage = urlToImage,
    )

    companion object {
        private val RECOMMENDED_TOPICS = listOf(
            "Apple",
            "Google",
            "Facebook", // update to new company name "meta"?
        )
    }

}