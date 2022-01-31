package com.example.newsreader.network.requests

import com.example.newsreader.network.data.EverythingQueryResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsQuery {

    @GET("everything/q={query}")
    fun query(@Path("query") q: String): Call<EverythingQueryResult>

}
