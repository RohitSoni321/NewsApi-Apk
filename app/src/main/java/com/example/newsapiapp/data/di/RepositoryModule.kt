package com.example.newsapiapp.data.di

import androidx.room.ProvidedAutoMigrationSpec
import com.example.newsapiapp.data.repository.NewsRepositoryImpl
import com.example.newsapiapp.data.repository.newsDataSource.NewsLocalDataSource
import com.example.newsapiapp.data.repository.newsDataSource.NewsRemoteDataSource
import com.example.newsapiapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideNewsRepository(newsRemoteDataSource: NewsRemoteDataSource,newsLocalDataSource: NewsLocalDataSource): NewsRepository{
        return NewsRepositoryImpl(newsRemoteDataSource,newsLocalDataSource)
    }

}