package com.example.newsreader.dependencyinjection

import android.content.Context
import com.example.newsreader.R
import com.example.newsreader.network.NewsApiContract
import com.example.newsreader.network.NewsApiProvider
import com.example.newsreader.newsfeed.NewsFeedUseCase
import com.kwabenaberko.newsapilib.NewsApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ProviderModule {

    @Provides
    @Singleton
    fun provideNewsApiContract(newsApiClient: NewsApiClient): NewsApiContract =
        NewsApiProvider(newsApiClient)

    @Provides
    fun provideNewsFeedUseCase(newsApiProvider: NewsApiProvider): NewsFeedUseCase =
        NewsFeedUseCase(newsApiProvider)

    @Provides
    fun provideNewsApiClient(@ApplicationContext appContext: Context): NewsApiClient {
        // placed in secret file, add your own news api key to compile
        return NewsApiClient(appContext.getString(R.string.news_api_key))
    }

}
