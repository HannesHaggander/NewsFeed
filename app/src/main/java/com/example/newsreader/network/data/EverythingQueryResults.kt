package com.example.newsreader.network.data

data class EverythingQueryResult(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
