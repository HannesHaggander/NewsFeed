package com.example.newsreader.network.requests

import com.example.newsreader.network.data.EverythingQueryResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsQuery {

    @GET("everything")
    fun query(
        @Query("q") q: String,
        @Query("sortBy") sortBy: String = SortNewsBy.PUBLISHED_AT.value,
        @Query("language") language: String = NewsLanguage.ENGLISH.code,
        @Query("page") page: Int = 1
    ): Call<EverythingQueryResult>

}
