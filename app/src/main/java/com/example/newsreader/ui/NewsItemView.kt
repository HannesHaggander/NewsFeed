package com.example.newsreader.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsreader.newsfeed.data.ArticleItemData

@Composable
fun NewsItemView(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    articleItemData: ArticleItemData,
) {
    Card(modifier = modifier) {
        Column(modifier = modifier) {
            Text(text = articleItemData.title)
            Text(text = articleItemData.description)
        }
    }
}

@Composable
@Preview
fun NewsItemPreview() {
    NewsItemView(
        articleItemData = ArticleItemData(
            url = "url",
            title = "title",
            description = "description",
            publishedAt = "published",
            urlToImage = "url to image",
        )
    )
}