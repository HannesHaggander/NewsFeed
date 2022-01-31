package com.example.newsreader.newsfeed.data

import java.time.OffsetDateTime
import java.util.*

data class ArticleItemData(
    val id: UUID = UUID.randomUUID(),
    val url: String = "",
    val title: String = "",
    val description: String = "",
    val publishedAt: OffsetDateTime?,
    val urlToImage: String,
) {
    companion object {
        fun empty(): ArticleItemData = ArticleItemData(
            url = "",
            title = "",
            description = "",
            publishedAt = OffsetDateTime.now(),
            urlToImage = ""
        )
    }
}
