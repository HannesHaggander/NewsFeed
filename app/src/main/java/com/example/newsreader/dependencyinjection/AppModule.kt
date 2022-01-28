package com.example.newsreader.dependencyinjection

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [ProviderModule::class])
class AppModule