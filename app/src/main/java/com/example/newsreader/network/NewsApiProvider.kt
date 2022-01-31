package com.example.newsreader.network

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
    private val retrofit: Retrofit,
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
                                ?.map { article -> article.toDomainModel() }
                                ?.let { domainArticles ->
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

    private fun <T> String.toResultFailure() = Result.failure<T>(Throwable(this))
}
