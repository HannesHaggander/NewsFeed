package com.example.newsreader.network

import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsApiProvider @Inject constructor(
    private val newsApiClient: NewsApiClient
) : NewsApiContract {

    suspend fun getCategory(category: String) =
        suspendCoroutine<Result<ArticleResponse>> { suspended ->
            if (category.isBlank()) {
                throw IllegalArgumentException("Category can not be blank")
            }

            val request = TopHeadlinesRequest
                .Builder()
                .category(category)
                .build()

            val callback = object : NewsApiClient.ArticlesResponseCallback {
                override fun onSuccess(response: ArticleResponse?) {
                    val result = response
                        ?.let { nonNull -> Result.success(nonNull) }
                        ?: Result.failure(NULL_VALUE_THROWABLE)
                    suspended.resume(result)
                }

                override fun onFailure(error: Throwable?) {
                    suspended.resume(Result.failure(error ?: NULL_VALUE_THROWABLE))
                }
            }

            newsApiClient.getTopHeadlines(request, callback)
        }

    companion object {
        private val NULL_VALUE_THROWABLE = Throwable("Value was null")
    }
}
