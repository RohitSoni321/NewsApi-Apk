package com.example.newsapiapp.data.di

import android.app.Application
import androidx.room.Room
import com.example.newsapiapp.data.db.ArticleDao
import com.example.newsapiapp.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideNewsDatabase(app: Application): ArticleDatabase{
        return Room.databaseBuilder(app, ArticleDatabase::class.java,name="news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.getArticleDao()
    }

}