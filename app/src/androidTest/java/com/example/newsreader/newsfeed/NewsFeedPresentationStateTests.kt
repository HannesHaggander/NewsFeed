package com.example.newsreader.newsfeed

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsreader.helpers.TEST_TAG_OFFLINE_MODE_INDICATOR
import com.example.newsreader.newsfeed.mockdata.articleItemDataMock
import com.example.newsreader.ui.newsfeed.states.PresentArticles
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class NewsFeedPresentationStateTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenOfflineState_whenPresentingArticles_thenShowClickableOfflineIndicator() {
        with(composeTestRule) {
            setContent {
                PresentArticles(
                    articles = listOf(articleItemDataMock()),
                    isOffline = true,
                    onArticleClick = { },
                    onRetryConnection = { }
                )
            }

            onNodeWithTag(TEST_TAG_OFFLINE_MODE_INDICATOR).run {
                assertExists()
                assertHasClickAction()
            }
        }
    }

    @Test
    fun givenOnlineState_whenPresentingArticles_thenHideOfflineModeIndicator() {
        with(composeTestRule) {
            setContent {
                PresentArticles(
                    articles = listOf(articleItemDataMock()),
                    isOffline = false,
                    onArticleClick = { },
                    onRetryConnection = { }
                )
            }

            onNodeWithTag(TEST_TAG_OFFLINE_MODE_INDICATOR).assertDoesNotExist()
        }
    }
}
