package com.example.newsreader.newsfeed

import com.example.newsreader.network.data.Article
import com.example.newsreader.newsfeed.data.ArticleItemData
import java.time.OffsetDateTime
import java.util.*

fun Article.toDomainModel(): ArticleItemData = ArticleItemData(
    id = UUID.randomUUID(),
    url = url,
    title = title,
    description = description,
    publishedAt = runCatching { OffsetDateTime.parse(publishedAt) }.getOrNull(),
    urlToImage = urlToImage.orEmpty(),
)
