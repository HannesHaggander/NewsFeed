package com.example.newsreader.network

import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsApiProvider @Inject constructor(
    private val newsApiClient: NewsApiClient
) : NewsApiContract {

    override suspend fun getCategory(category: String): Result<ArticleResponse> =
        suspendCoroutine { suspended ->
            if (category.isBlank()) {
                suspended.resume(Result.failure(Throwable("Category can not be blank")))
                return@suspendCoroutine
            }

//            val request = TopHeadlinesRequest
//                .Builder()
//                .category(category)
//                .build()

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

            //newsApiClient.getTopHeadlines(request, callback)

            val request = EverythingRequest.Builder().q(category).build()
            newsApiClient.getEverything(request, callback)
        }

    companion object {
        private val NULL_VALUE_THROWABLE = Throwable("Value was null")
    }
}
