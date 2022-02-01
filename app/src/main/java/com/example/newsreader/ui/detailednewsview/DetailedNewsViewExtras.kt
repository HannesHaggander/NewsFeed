package com.example.newsreader.ui.detailednewsview

import androidx.navigation.Navigator
import com.example.newsreader.newsfeed.data.ArticleItemData
import java.io.Serializable

class DetailedNewsViewExtras(val articleItemData: ArticleItemData) : Navigator.Extras, Serializable {

    companion object {
        const val EXTRAS_KEY = "DetailedNewsViewExtras"
    }
}
