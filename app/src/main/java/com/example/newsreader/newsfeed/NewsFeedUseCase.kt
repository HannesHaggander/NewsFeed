package com.example.newsreader.newsfeed

import com.example.newsreader.network.NewsApiContract
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.kwabenaberko.newsapilib.models.Article
import java.util.*
import javax.inject.Inject

class NewsFeedUseCase @Inject constructor(
    private val newsApiContract: NewsApiContract
) {
    suspend fun getTopic(topic: String): List<ArticleItemData> = newsApiContract
        .getCategory(topic)
        .getOrNull()
        ?.articles
        .orEmpty()
        .map { article -> article.toDomainModel() }

    private fun Article.toDomainModel(): ArticleItemData = ArticleItemData(
        id = UUID.randomUUID(),
        url = url,
        title = title,
        description = description,
        publishedAt = publishedAt,
        urlToImage = urlToImage,
    )
}
