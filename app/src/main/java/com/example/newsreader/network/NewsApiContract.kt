package com.example.newsreader.network

import com.kwabenaberko.newsapilib.models.response.ArticleResponse

interface NewsApiContract {
    suspend fun getCategory(category: String): Result<ArticleResponse>
}