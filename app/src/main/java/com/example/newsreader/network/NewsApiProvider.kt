package com.example.newsreader.network

import android.util.Log
import com.example.newsreader.database.LocalRoomDatabase
import com.example.newsreader.network.data.EverythingQueryResult
import com.example.newsreader.network.requests.NewsQuery
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.newsfeed.toDomainModel
import com.example.newsreader.newsfeed.toEntityModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsApiProvider @Inject constructor(
    private val retrofit: Retrofit,
    private val localRoomDatabase: LocalRoomDatabase,
) : NewsApiContract {

    private val newsQuery: NewsQuery = retrofit.create(NewsQuery::class.java)

    override suspend fun queryRequest(query: String): Result<List<ArticleItemData>> =
        suspendCoroutine { suspended ->
            if (query.isBlank()) {
                suspended.resume("Category can not be blank".toResultFailure())
                return@suspendCoroutine
            }

            newsQuery
                .query(query)
                .enqueue(object : Callback<EverythingQueryResult> {
                    override fun onResponse(
                        call: Call<EverythingQueryResult>,
                        response: Response<EverythingQueryResult>
                    ) {
                        if (response.isSuccessful) {
                            response
                                .body()
                                ?.articles
                                ?.mapNotNull { article ->
                                    runCatching { article.toDomainModel() }
                                        .onFailure { error ->
                                            Log.w(
                                                NewsApiProvider::class.java.simpleName,
                                                "Failed to parse news data to domain model",
                                                error
                                            )
                                        }
                                        .getOrNull()
                                }
                                ?.let { domainArticles ->
                                    with(localRoomDatabase.articleItemDao()) {
                                        nukeTable()
                                        insertAll(domainArticles.map { it.toEntityModel() })
                                    }
                                    suspended.resume(Result.success(domainArticles))
                                }
                                ?: suspended.resume("Failed to parse response data".toResultFailure())
                        } else {
                            suspended.resume("Response was unsuccessful".toResultFailure())
                        }
                    }

                    override fun onFailure(call: Call<EverythingQueryResult>, t: Throwable) {
                        suspended.resume((t.message ?: "Failed to perform query").toResultFailure())
                    }

                })
        }

    override suspend fun queryDatabase(): Result<List<ArticleItemData>> = localRoomDatabase
        .articleItemDao()
        .getAllArticles()
        .runCatching { map { it.toDomainModel() } }

    private fun <T> String.toResultFailure() = Result.failure<T>(Throwable(this))
}
