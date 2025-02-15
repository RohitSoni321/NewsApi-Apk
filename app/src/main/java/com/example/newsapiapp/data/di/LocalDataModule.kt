package com.example.newsapiapp.data.di

import androidx.room.ProvidedAutoMigrationSpec
import com.example.newsapiapp.data.db.ArticleDao
import com.example.newsapiapp.data.repository.newsDataSource.NewsLocalDataSource
import com.example.newsapiapp.data.repository.newsDataSourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    fun providesLocalDataSource(articleDao: ArticleDao): NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDao)
    }

}