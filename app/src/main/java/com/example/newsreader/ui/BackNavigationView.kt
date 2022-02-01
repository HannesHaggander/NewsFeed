package com.example.newsreader.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsreader.R

@Composable
fun BackNavigationView(
    modifier: Modifier = Modifier
        .height(48.dp)
        .width(48.dp)
        .padding(8.dp),
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = R.drawable.ic_arrow_left),
        contentDescription = stringResource(id = R.string.navigation_back),
        modifier = modifier.clickable { onClick.invoke() }
    )
}

@Preview
@Composable
fun BackPreview() {
    BackNavigationView {
        // do nothing
    }
}
