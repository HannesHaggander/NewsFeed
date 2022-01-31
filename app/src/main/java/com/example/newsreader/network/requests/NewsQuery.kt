package com.example.newsreader.network.requests

import com.example.newsreader.network.data.EverythingQueryResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsQuery {

    @GET("everything")
    fun query(@Query("q") q: String): Call<EverythingQueryResult>

}
