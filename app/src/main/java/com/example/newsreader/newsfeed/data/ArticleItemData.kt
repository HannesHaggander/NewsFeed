package com.example.newsreader.newsfeed.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime
import java.util.*

@Parcelize
data class ArticleItemData(
    @Transient val id: UUID,
    val url: String,
    val title: String,
    val description: String,
    val publishedAt: OffsetDateTime?,
    val urlToImage: String,
) : Parcelable {
    companion object {
        fun empty(): ArticleItemData = ArticleItemData(
            id = UUID.randomUUID(),
            url = "",
            title = "",
            description = "",
            publishedAt = OffsetDateTime.now(),
            urlToImage = ""
        )
    }
}
