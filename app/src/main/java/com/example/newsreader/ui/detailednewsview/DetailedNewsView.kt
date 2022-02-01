package com.example.newsreader.ui.detailednewsview

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.newsreader.newsfeed.data.ArticleItemData

@Composable
fun DetailedNewsView(
    navController: NavController,
    articleItemData: ArticleItemData
) {
    Text(text = "Detailed news view for article: ${articleItemData.title}")
}
