package com.example.newsreader.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val retrofitConfig: Retrofit = Retrofit
    .Builder()
    .baseUrl("https://newsapi.org/v2/")
    .addConverterFactory(
        MoshiConverterFactory
            .create(
                Moshi
                    .Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
    )
    .build()
