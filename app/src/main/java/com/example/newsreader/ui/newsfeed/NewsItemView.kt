package com.example.newsreader.ui.newsfeed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.newsreader.R
import com.example.newsreader.helpers.fullDateTimeFormat
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.AppStyle
import com.example.newsreader.ui.AppTheme
import java.time.OffsetDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsItemView(
    modifier: Modifier = Modifier
        .height(160.dp)
        .padding(8.dp),
    articleItemData: ArticleItemData,
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .wrapContentHeight(),
        onClick = onItemClick,
        elevation = 4.dp,
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (textContainer, image) = createRefs()

            Column(modifier = modifier
                .padding(start = 8.dp, end = 4.dp)
                .constrainAs(textContainer) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(0.66f)
                }
            ) {
                Text(
                    text = articleItemData.title,
                    style = AppStyle.title,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = articleItemData.description,
                    style = AppStyle.body,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )

                articleItemData.publishedAt?.let { publishDate ->
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = publishDate.format(fullDateTimeFormat),
                        style = AppStyle.utility,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(image) {
                        end.linkTo(parent.end)
                        top.linkTo(textContainer.top)
                        bottom.linkTo(textContainer.bottom)
                        start.linkTo(textContainer.end)
                        width = Dimension.fillToConstraints
                    },
                painter = rememberImagePainter(
                    data = articleItemData.urlToImage,
                    builder = {
                        ImageRequest
                            .Builder(context = LocalContext.current)
                            .crossfade(true)
                            .fallback(drawableResId = R.drawable.ic_image_broken)
                            .build()
                    }
                ),
                contentDescription = "image for ${articleItemData.title}",
            )
        }
    }
}

@Composable
@Preview(name = "Short title and description")
fun NewsItemPreview() {
    AppTheme {
        NewsItemView(
            articleItemData = ArticleItemData(
                url = "url",
                title = "title",
                description = "description",
                publishedAt = OffsetDateTime.now(),
                urlToImage = "url to image",
            )
        )
    }
}

@Composable
@Preview(name = "Long title ")
fun NewsItemLongTitle() {
    AppTheme {
        NewsItemView(
            articleItemData = ArticleItemData(
                url = "url",
                title = "This is a far too long title and we will have to cut it",
                description = "This description is also very long but it will write over several lines just to showcase that it works fine even though it cuts off",
                publishedAt = OffsetDateTime.now(),
                urlToImage = "url to image",
            )
        )
    }
}
