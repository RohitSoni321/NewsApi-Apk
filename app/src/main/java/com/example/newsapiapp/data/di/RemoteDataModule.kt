package com.example.newsapiapp.data.di

import androidx.room.ProvidedAutoMigrationSpec
import com.example.newsapiapp.data.api.NewsAPIService
import com.example.newsapiapp.data.db.ArticleDao
import com.example.newsapiapp.data.repository.newsDataSource.NewsRemoteDataSource
import com.example.newsapiapp.data.repository.newsDataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Provides
    fun provideNewsRemoteDataSource(newsAPIService: NewsAPIService): NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}