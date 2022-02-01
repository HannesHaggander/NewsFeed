package com.example.newsreader.ui

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppStyle {
    val title: TextStyle
        get() = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.5).sp
        )

    val body: TextStyle
        get() = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp
        )

    val utility: TextStyle
        get() = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp
        )
}

val lightColors = Colors(
    primary = Color(0xfffb8c00),
    primaryVariant = Color(0xffef6c00),
    secondary = Color(0xffffa000),
    secondaryVariant = Color(0xffff8f00),
    background = Color(0xffeeeeee),
    surface = Color(0xffffffff),
    error = Color(0xffff3d00),
    onPrimary = Color(0xff212121),
    onSecondary = Color(0xff212121),
    onBackground = Color(0xff212121),
    onSurface = Color(0xff212121),
    onError = Color(0xff212121),
    isLight = false,
)

@Composable
fun AppTheme(
    themeColors: Colors = lightColors,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = themeColors,
        content = content,
    )
}