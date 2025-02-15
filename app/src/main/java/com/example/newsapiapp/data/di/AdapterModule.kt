package com.example.newsapiapp.data.di

import com.example.newsapiapp.presentation.adapter.NewsAdapter
import com.example.newsapiapp.presentation.adapter.SavedNewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    fun provideNewsAdapter():NewsAdapter{
        return NewsAdapter()
    }

    @Provides
    fun provideSavedNewsAdapter():SavedNewsAdapter{
        return SavedNewsAdapter()
    }

}