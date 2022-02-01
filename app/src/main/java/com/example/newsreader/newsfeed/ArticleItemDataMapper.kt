package com.example.newsreader.newsfeed

import com.example.newsreader.database.entities.ArticleItemEntity
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

fun ArticleItemEntity.toDomainModel(): ArticleItemData = ArticleItemData(
    id = this.id,
    url = this.url,
    title = this.title,
    description = this.description,
    publishedAt = this.publishedAt,
    urlToImage = this.urlToImage,
)

fun ArticleItemData.toEntityModel(): ArticleItemEntity = ArticleItemEntity(
    id = this.id,
    url = this.url,
    title = this.title,
    description = this.description,
    publishedAt = this.publishedAt,
    urlToImage = this.urlToImage,
)
