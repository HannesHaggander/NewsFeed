package com.example.newsreader.network

import com.example.newsreader.newsfeed.data.ArticleItemData

interface NewsApiContract {
    suspend fun getCategory(category: String): Result<List<ArticleItemData>>
}