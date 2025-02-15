package com.example.newsapiapp.data.di

import com.example.newsapiapp.domain.repository.NewsRepository
import com.example.newsapiapp.domain.usecases.DeleteSavedUsecases
import com.example.newsapiapp.domain.usecases.GetNewsHeadLinesUsecases
import com.example.newsapiapp.domain.usecases.GetSavedNewsUsecases
import com.example.newsapiapp.domain.usecases.GetSearchNewsUsecases
import com.example.newsapiapp.domain.usecases.SaveNewsUsecases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetTopHeadLinesUsecases(newsRepository: NewsRepository): GetNewsHeadLinesUsecases{
        return GetNewsHeadLinesUsecases(newsRepository)
    }

    @Provides
    fun provideGetSaveNewsUsecases(newsRepository: NewsRepository): GetSavedNewsUsecases{
        return GetSavedNewsUsecases(newsRepository)
    }

    @Provides
    fun provideDeleteSavedUsecases(newsRepository: NewsRepository): DeleteSavedUsecases{
        return DeleteSavedUsecases(newsRepository)
    }
    @Provides
    fun provideSaveNewsUsecases(newsRepository: NewsRepository): SaveNewsUsecases {
        return SaveNewsUsecases(newsRepository)
    }



    @Provides
    fun provideGetSearchNewsUsecases(newsRepository: NewsRepository): GetSearchNewsUsecases {
        return GetSearchNewsUsecases(newsRepository)
    }
}