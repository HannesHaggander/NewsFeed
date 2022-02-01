package com.example.newsreader.ui.detailednewsview

import android.text.Html
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.newsreader.R
import com.example.newsreader.helpers.fullDateTimeFormat
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.AppStyle
import com.example.newsreader.ui.AppTheme
import com.example.newsreader.ui.BackNavigationView
import java.time.OffsetDateTime
import java.util.*

@Composable
fun DetailedNewsView(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
    articleItemData: ArticleItemData,
    onBackPress: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(
                    state = scrollState,
                    enabled = true,
                )
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            BackNavigationView(onClick = onBackPress)

            if (articleItemData.urlToImage.isNotEmpty()) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = rememberImagePainter(
                        data = articleItemData.urlToImage,
                        builder = {
                            ImageRequest
                                .Builder(context = LocalContext.current)
                                .crossfade(true)
                                .placeholder(
                                    AppCompatResources.getDrawable(
                                        LocalContext.current,
                                        R.drawable.ic_image_broken
                                    )
                                )
                                .error(drawableResId = R.drawable.ic_image_broken)
                                .fallback(drawableResId = R.drawable.ic_image_broken)
                                .build()
                        }
                    ),
                    contentDescription = stringResource(
                        id = R.string.content_description_image,
                        articleItemData.title
                    ),
                )
            }

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                text = articleItemData.title,
                style = AppStyle.title,
                color = MaterialTheme.colors.onSurface,
            )

            articleItemData.publishedAt?.let { publishDate ->
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(
                        id = R.string.article_published_at_date,
                        publishDate.format(fullDateTimeFormat)
                    ),
                    style = AppStyle.utility,
                )
            }

            Text(
                modifier = Modifier.padding(16.dp),
                text = Html
                    .fromHtml(articleItemData.description, Html.FROM_HTML_MODE_LEGACY)
                    .toString(),
                style = AppStyle.body,
                color = MaterialTheme.colors.onSurface,
            )
        }
    }
}

@Preview
@Composable
fun PreviewDetailedNews() {
    AppTheme {
        DetailedNewsView(
            articleItemData = ArticleItemData(
                id = UUID.randomUUID(),
                url = "url",
                title = "Lorem ipsum dolor sit amet",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
                publishedAt = OffsetDateTime.now(),
                urlToImage = "",
            )
        )
    }
}
