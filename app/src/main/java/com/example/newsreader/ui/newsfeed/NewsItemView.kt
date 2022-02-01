package com.example.newsreader.ui.newsfeed

import android.text.Html
import androidx.appcompat.content.res.AppCompatResources
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
import androidx.compose.ui.res.stringResource
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
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsItemView(
    modifier: Modifier = Modifier,
    articleItemData: ArticleItemData,
    onItemClick: () -> Unit = {}
) {
    val hasImage = articleItemData.urlToImage.isNotEmpty()

    Card(
        modifier = modifier
            .wrapContentHeight()
            .heightIn(0.dp, 150.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(4.dp),
        onClick = onItemClick,
        elevation = 4.dp,
    ) {
        ConstraintLayout(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
        ) {
            val (textContainer, image) = createRefs()

            Column(modifier = modifier
                .padding(8.dp)
                .heightIn(0.dp, 150.dp)
                .constrainAs(textContainer) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(if (hasImage) 0.66f else 1f)
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
                    text = Html
                        .fromHtml(articleItemData.description, Html.FROM_HTML_MODE_LEGACY)
                        .toString(),
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

            if (hasImage) {
                Image(
                    modifier = modifier
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
        }
    }
}

@Composable
@Preview(name = "Short title and description")
fun NewsItemPreview() {
    AppTheme {
        NewsItemView(
            articleItemData = ArticleItemData(
                id = UUID.randomUUID(),
                url = "url",
                title = "title",
                description = "description",
                publishedAt = OffsetDateTime.now(),
                urlToImage = "",
            )
        )
    }
}

@Composable
@Preview(name = "Filled title and description")
fun NewsItemLongTitle() {
    AppTheme {
        NewsItemView(
            articleItemData = ArticleItemData(
                id = UUID.randomUUID(),
                url = "url",
                title = "This is a far too long title and we will have to cut it",
                description = "This description is also very long but it will write over several lines just to showcase that it works fine even though it cuts off",
                publishedAt = OffsetDateTime.now(),
                urlToImage = "url to image",
            )
        )
    }
}

@Preview(name = "Items in column list")
@Composable
fun NewsItemList() {
    AppTheme {
        Column {
            NewsItemView(
                articleItemData = ArticleItemData(
                    id = UUID.randomUUID(),
                    url = "url",
                    title = "Title 1",
                    description = "Description for title 1",
                    publishedAt = OffsetDateTime.now(),
                    urlToImage = "",
                )
            )
            NewsItemView(
                articleItemData = ArticleItemData(
                    id = UUID.randomUUID(),
                    url = "url",
                    title = "Title 2",
                    description = "description for title 2",
                    publishedAt = OffsetDateTime.now(),
                    urlToImage = "url to image",
                )
            )
            NewsItemView(
                articleItemData = ArticleItemData(
                    id = UUID.randomUUID(),
                    url = "url",
                    title = "Title 3",
                    description = "description for title 3",
                    publishedAt = OffsetDateTime.now(),
                    urlToImage = "url to image",
                )
            )
        }
    }
}
