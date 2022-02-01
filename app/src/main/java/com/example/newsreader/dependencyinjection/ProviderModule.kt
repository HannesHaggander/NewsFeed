package com.example.newsreader.dependencyinjection

import android.content.Context
import com.example.newsreader.R
import com.example.newsreader.database.LocalRoomDatabase
import com.example.newsreader.network.NewsApiContract
import com.example.newsreader.network.NewsApiProvider
import com.example.newsreader.newsfeed.NewsFeedUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ProviderModule {

    @Provides
    @Singleton
    fun provideNewsApiContract(
        retrofit: Retrofit,
        localRoomDatabase: LocalRoomDatabase
    ): NewsApiContract = NewsApiProvider(retrofit, localRoomDatabase)

    @Provides
    fun provideNewsFeedUseCase(
        newsApiProvider: NewsApiProvider,
        localRoomDatabase: LocalRoomDatabase
    ): NewsFeedUseCase = NewsFeedUseCase(newsApiProvider, localRoomDatabase)

    @Provides
    @Singleton
    fun provideRetrofitConfig(domainClient: OkHttpClient): Retrofit = Retrofit
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
        .client(domainClient)
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext appContext: Context) = OkHttpClient
        .Builder()
        .addInterceptor { chain ->
            var request = chain.request()
            val modifiedApiKeyUrl = request
                .url()
                .newBuilder()
                .addQueryParameter("apiKey", appContext.getString(R.string.news_api_key))
                .build()

            val updatedRequest = request
                .newBuilder()
                .url(modifiedApiKeyUrl)
                .build()

            chain.proceed(updatedRequest)
        }
        .build()

    @Provides
    @Singleton
    fun provideLocalRoomDatabase(@ApplicationContext appContext: Context): LocalRoomDatabase =
        LocalRoomDatabase.create(appContext)

}
