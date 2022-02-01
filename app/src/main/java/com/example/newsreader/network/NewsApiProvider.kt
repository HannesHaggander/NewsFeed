package com.example.newsreader.network

import android.util.Log
import com.example.newsreader.database.LocalRoomDatabase
import com.example.newsreader.network.data.EverythingQueryResult
import com.example.newsreader.network.requests.NewsQuery
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.newsfeed.toDomainModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsApiProvider @Inject constructor(
    retrofit: Retrofit,
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
                            suspended.resume(onSuccessfulResponse(response))
                        } else {
                            suspended.resume("Response was unsuccessful".toResultFailure())
                        }
                    }

                    override fun onFailure(call: Call<EverythingQueryResult>, t: Throwable) {
                        suspended.resume((t.message ?: "Failed to perform query").toResultFailure())
                    }

                })
        }

    private fun onSuccessfulResponse(response: Response<EverythingQueryResult>): Result<List<ArticleItemData>> {
        response
            .body()
            ?.articles
            ?.mapNotNull { article ->
                runCatching { article.toDomainModel() }
                    .onFailure { error ->
                        Log.w(TAG, "Failed to parse news data to domain model", error)
                    }
                    .getOrNull()
            }
            ?.let { domainArticles -> return Result.success(domainArticles) }
            ?: return Result.failure(Throwable("Failed to find articles in response"))
    }

    override suspend fun queryDatabaseForRecentArticles(): Result<List<ArticleItemData>> =
        localRoomDatabase
            .articleItemDao()
            .getAllArticles()
            .runCatching { map { it.toDomainModel() } }
            .getOrNull()
            ?.let { articles -> Result.success(articles) }
            ?: Result.failure(Throwable("Failed to query database"))

    private fun <T> String.toResultFailure() = Result.failure<T>(Throwable(this))

    companion object {
        private val TAG = NewsApiProvider::class.java.simpleName
    }
}
