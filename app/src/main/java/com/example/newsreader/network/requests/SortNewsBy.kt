package com.example.newsreader.network.requests

enum class SortNewsBy(val value: String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt"),
}
