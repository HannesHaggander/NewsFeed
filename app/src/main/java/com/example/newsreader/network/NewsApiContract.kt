package com.example.newsreader.network

import com.example.newsreader.newsfeed.data.ArticleItemData

interface NewsApiContract {
    suspend fun queryRequest(query: String): Result<List<ArticleItemData>>

    suspend fun queryDatabaseForRecentArticles(): Result<List<ArticleItemData>>
}
