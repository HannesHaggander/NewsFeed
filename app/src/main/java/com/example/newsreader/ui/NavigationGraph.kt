package com.example.newsreader.ui

import androidx.navigation.NavController
import com.example.newsreader.helpers.VIEW_ROUTE_DETAILED_ARTICLE
import com.example.newsreader.newsfeed.data.ArticleItemData

sealed class NavigationGraph {
    companion object {
        fun toArticleDetail(navController: NavController, articleItemData: ArticleItemData) {
            navController.navigate("$VIEW_ROUTE_DETAILED_ARTICLE/article=$articleItemData")
        }
    }
}