package com.example.newsreader.newsfeed.mockdata

import com.example.newsreader.newsfeed.data.ArticleItemData
import java.time.OffsetDateTime
import java.util.*

fun articleItemDataMock() = ArticleItemData(
    id = UUID.randomUUID(),
    url = "url",
    title = "title",
    description = "description",
    publishedAt = OffsetDateTime.now(),
    urlToImage = "url image",
)
