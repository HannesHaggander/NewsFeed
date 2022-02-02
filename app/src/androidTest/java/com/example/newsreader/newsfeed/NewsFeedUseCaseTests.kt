package com.example.newsreader.newsfeed

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsreader.database.LocalRoomDatabase
import com.example.newsreader.network.NewsApiProvider
import com.example.newsreader.newsfeed.mockdata.articleItemDataMock
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class NewsFeedUseCaseTests {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenEmptyDatabase_whenSuccessfullyFetchingDataFromNetwork_thenStoreValuesInDatabase() =
        runTest {
            val inMemoryDatabase = Room
                .inMemoryDatabaseBuilder(
                    InstrumentationRegistry.getInstrumentation().targetContext,
                    LocalRoomDatabase::class.java
                )
                .build()

            val expectedItemData = listOf(articleItemDataMock())

            val mockedNewsApiProvider = mockkClass(NewsApiProvider::class) {
                coEvery { queryRequest(any()) } returns Result.success(expectedItemData)
            }

            NewsFeedUseCase(
                newsApiContract = mockedNewsApiProvider,
                localRoomDatabase = inMemoryDatabase
            ).let { useCase ->
                useCase.getTopic("a topic")
                inMemoryDatabase
                    .articleItemDao()
                    .getAllArticles()
                    .map { entity -> entity.toDomainModel() }
                    .let { storedData -> Assert.assertEquals(expectedItemData, storedData) }
            }
        }
}