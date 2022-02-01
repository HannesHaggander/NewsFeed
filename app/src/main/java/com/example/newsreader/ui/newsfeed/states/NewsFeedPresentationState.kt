package com.example.newsreader.ui.newsfeed.states

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.newsfeed.NewsItemView

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PresentArticles(
    articles: List<ArticleItemData>,
    onArticleClick: (ArticleItemData) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState,
                enabled = true,
            )
            .background(MaterialTheme.colors.background),
    ) {
        articles.forEachIndexed { index, item ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(
                        durationMillis = 1000,
                        delayMillis = index * 500,
                        easing = LinearEasing
                    )
                )
            ) {
                NewsItemView(
                    articleItemData = item,
                    onItemClick = { onArticleClick.invoke(item) }
                )
            }
        }
    }
}