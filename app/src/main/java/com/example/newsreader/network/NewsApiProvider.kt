package com.example.newsreader.network

import com.example.newsreader.network.data.EverythingQueryResult
import com.example.newsreader.network.requests.NewsQuery
import com.example.newsreader.newsfeed.data.ArticleItemData
import com.example.newsreader.newsfeed.toDomainModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsApiProvider @Inject constructor(
    private val newsApiClient: NewsApiClient,
    private val retrofit: Retrofit,
) : NewsApiContract {

    val newsQuery: NewsQuery = retrofit.create(NewsQuery::class.java)

    suspend fun tmp(query: String): Result<List<ArticleItemData>> = suspendCoroutine { suspended ->
        newsQuery
            .query(query)
            .enqueue(object: Callback<EverythingQueryResult>{
                override fun onResponse(
                    call: Call<EverythingQueryResult>,
                    response: Response<EverythingQueryResult>
                ) {
                    if(response.isSuccessful){
                        response
                            .body()
                            ?.articles
                            ?.mapNotNull {  }
                            ?:
                    } else {
                        suspended.resume(Result.failure(Throwable("Response was unsuccessful")))
                        return
                    }
                }

                override fun onFailure(call: Call<EverythingQueryResult>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    override suspend fun getCategory(category: String): Result<List<ArticleItemData>> =
        suspendCoroutine { suspended ->
            if (category.isBlank()) {
                suspended.resume(Result.failure(Throwable("Category can not be blank")))
                return@suspendCoroutine
            }

            val callback = object : NewsApiClient.ArticlesResponseCallback {
                override fun onSuccess(response: ArticleResponse?) {
                    val result = response
                        ?.let { nonNull ->
                            val mappedItems = nonNull
                                .articles
                                .orEmpty()
                                .mapNotNull { runCatching { it.toDomainModel() }.getOrNull() }
                            Result.success(mappedItems)
                        }
                        ?: Result.failure(NULL_VALUE_THROWABLE)
                    suspended.resume(result)
                }

                override fun onFailure(error: Throwable?) {
                    suspended.resume(Result.failure(error ?: NULL_VALUE_THROWABLE))
                }
            }

            val request = EverythingRequest
                .Builder()
                .sortBy(SORT_BY_PUBLISH_DATE)
                .q(category)
                .build()

            newsApiClient.getEverything(request, callback)
        }

    companion object {
        private val NULL_VALUE_THROWABLE = Throwable("Value was null")
        private const val SORT_BY_PUBLISH_DATE = "publishedAt"
    }
}
