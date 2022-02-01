package com.example.newsreader.ui.newsfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsreader.R
import com.example.newsreader.newsfeed.NewsFeedViewModel
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.AppTheme
import com.example.newsreader.ui.Loading
import com.example.newsreader.ui.newsfeed.states.NewsFeedError
import com.example.newsreader.ui.newsfeed.states.NewsFeedViewState
import com.example.newsreader.ui.newsfeed.states.PresentArticles
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NewsFeedFragment : Fragment() {

    private val newsFeedViewModel: NewsFeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            NewsFeedViewState()
        }

        lifecycleScope.launchWhenResumed {
            newsFeedViewModel.currentViewState.collect()
        }
    }

    @Composable
    fun NewsFeedViewState() {
        val newsFeed = newsFeedViewModel
            .currentViewState
            .collectAsState()

        AppTheme {
            when (val state = newsFeed.value) {
                is NewsFeedViewState.Error -> NewsFeedError { newsFeedViewModel.updateViewState() }
                is NewsFeedViewState.Loading -> Loading(loadingText = stringResource(id = R.string.news_feed_loading_message))
                is NewsFeedViewState.Success -> PresentArticles(
                    articles = state.result,
                    onArticleClick = { article ->
                        val args = bundleOf().apply {
                            putParcelable(
                                ArticleItemData::class.java.simpleName,
                                article
                            )
                        }
                        findNavController().navigate(R.id.toDetailedNewsFragment, args = args)
                    }
                )
            }
        }
    }
}