package com.example.newsreader.dependencyinjection

import com.example.newsreader.network.NewsApiContract
import com.example.newsreader.network.NewsApiProvider
import com.example.newsreader.newsfeed.NewsFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ProviderModule {

    @Provides
    @Singleton
    fun provideNewsApiContract(): NewsApiContract = NewsApiProvider();

    @Provides
    fun provideNewsFeedUseCase(newsApiProvider: NewsApiProvider): NewsFeedUseCase =
        NewsFeedUseCase(newsApiProvider)

    @Provides
    fun provideNewsApiClient(){

    }

}