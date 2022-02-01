package com.example.newsreader.ui.detailednewsview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.newsreader.newsfeed.data.ArticleItemData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        val article = arguments
            ?.getParcelable<ArticleItemData>(ArticleItemData::class.java.simpleName)
            ?: throw IllegalArgumentException("Failed to find article item data in arguments")

        setContent {
            DetailedNewsView(
                navController = findNavController(),
                articleItemData = article
            )
        }
    }
}