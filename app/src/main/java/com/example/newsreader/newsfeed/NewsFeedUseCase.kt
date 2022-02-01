package com.example.newsreader.newsfeed

import com.example.newsreader.database.LocalRoomDatabase
import com.example.newsreader.network.NewsApiContract
import com.example.newsreader.newsfeed.data.ArticleItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsFeedUseCase @Inject constructor(
    private val newsApiContract: NewsApiContract,
    private val localRoomDatabase: LocalRoomDatabase,
) {
    suspend fun getTopic(topic: String): List<ArticleItemData> = newsApiContract
        .queryRequest(topic)
        .getOrNull()
        ?.also { articles -> setDatabase(articles, localRoomDatabase) }
        .orEmpty()

    suspend fun getStoredArticlesFromDatabase(): List<ArticleItemData> = newsApiContract
        .queryDatabaseForRecentArticles()
        .getOrNull()
        .orEmpty()

    private suspend fun setDatabase(articles: List<ArticleItemData>, db: LocalRoomDatabase) {
        withContext(Dispatchers.IO) {
            with(db.articleItemDao()) {
                nukeTable()
                insertAll(articles.map { it.toEntityModel() })
            }
        }
    }
}
