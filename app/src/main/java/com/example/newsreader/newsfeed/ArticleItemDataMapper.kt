package com.example.newsreader.newsfeed

import com.example.newsreader.network.data.Article
import com.example.newsreader.newsfeed.data.ArticleItemData
import java.time.OffsetDateTime

fun Article.toDomainModel(): ArticleItemData = ArticleItemData(
    url = url,
    title = title,
    description = description,
    publishedAt = runCatching { OffsetDateTime.parse(publishedAt) }.getOrNull(),
    urlToImage = urlToImage.orEmpty(),
)
