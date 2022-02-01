package com.example.newsreader.database.dataaccess

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsreader.database.entities.ArticleItemEntity

@Dao
interface ArticleItemDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<ArticleItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<ArticleItemEntity>)

    @Query("SELECT * FROM ARTICLES WHERE id=:articleId")
    fun getArticle(articleId: String): ArticleItemEntity

    @Query("DELETE FROM articles")
    fun nukeTable()

}
