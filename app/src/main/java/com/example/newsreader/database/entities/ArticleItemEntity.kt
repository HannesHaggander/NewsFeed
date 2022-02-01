package com.example.newsreader.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "articles")
data class ArticleItemEntity(
    @PrimaryKey val id: UUID,
    val url: String,
    val title: String,
    val description: String,
    @ColumnInfo(name = "published_at") val publishedAt: OffsetDateTime?,
    @ColumnInfo(name = "url_to_image") val urlToImage: String,
)