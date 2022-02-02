package com.example.newsreader.newsfeed

import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.ui.newsfeed.states.NewsFeedViewState
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class NewsFeedViewModelTests {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun givenEmptyState_whenFetchingNewsFromNetwork_thenShowNewValues() = runTest {
        val mockedUseCase = mockkClass(NewsFeedUseCase::class) {
            coEvery { getTopic(any()) } returns listOf(mockkClass(ArticleItemData::class))
            coEvery { getStoredArticlesFromDatabase() } returns emptyList()
        }

        NewsFeedViewModel(newsFeedUseCase = mockedUseCase).run {
            updateCurrentViewState()
            currentStateFlow
                .take(2)
                .collectIndexed { index, value ->
                    when (value) {
                        is NewsFeedViewState.Error -> {
                            Assert.fail("Should not throw error")
                        }
                        is NewsFeedViewState.Loading -> {
                            if (index != 0) {
                                Assert.fail("Invalid loading state step")
                            }
                        }
                        is NewsFeedViewState.Success -> {
                            if (index == 1) {
                                Assert.assertTrue(!value.isOffline)
                            } else {
                                Assert.fail("Unexpected success step, should occur on step 1, not $index")
                            }
                        }
                    }
                }
        }
    }

    @Test
    fun givenEmptyState_whenUpdatingFailingNetwork_thenGetOfflineMode() = runTest {
        val mockedUseCase = mockkClass(NewsFeedUseCase::class) {
            coEvery { getTopic(any()) } returns emptyList()
            coEvery { getStoredArticlesFromDatabase() } returns listOf(mockkClass(ArticleItemData::class))
        }

        NewsFeedViewModel(newsFeedUseCase = mockedUseCase).run {
            updateCurrentViewState()
            currentStateFlow
                .take(2)
                .collectIndexed { index, value ->
                    when (value) {
                        is NewsFeedViewState.Error -> Assert.fail("Should not throw error")
                        is NewsFeedViewState.Loading -> {
                            if (index != 0) {
                                Assert.fail("Invalid loading state step")
                            }
                        }
                        is NewsFeedViewState.Success -> {
                            if (index == 1) {
                                Assert.assertTrue(value.isOffline)
                            } else {
                                Assert.fail("Unexpected success step, should occur on step 1, not $index")
                            }
                        }
                    }
                }
        }
    }

    @Test
    fun givenEmptyState_whenNoDataPresent_thenShowError() = runTest {
        val mockedUseCase = mockkClass(NewsFeedUseCase::class) {
            coEvery { getTopic(any()) } returns emptyList()
            coEvery { getStoredArticlesFromDatabase() } returns emptyList()
        }

        NewsFeedViewModel(newsFeedUseCase = mockedUseCase).run {
            updateCurrentViewState()
            currentStateFlow
                .take(2)
                .collectIndexed { index, value ->
                    when (value) {
                        is NewsFeedViewState.Error -> {
                            if (index > 1) {
                                Assert.fail("Unexpected index for error state: $index")
                            }
                            // pass
                        }
                        is NewsFeedViewState.Loading -> {
                            if (index != 0) {
                                Assert.fail("Invalid loading state step")
                            }
                        }
                        is NewsFeedViewState.Success -> Assert.fail("Should not succeed")
                    }
                }
        }

    }
}
