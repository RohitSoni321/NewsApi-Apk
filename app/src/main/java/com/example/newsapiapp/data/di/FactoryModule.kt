package com.example.newsapiapp.data.di

import android.app.Application
import com.example.newsapiapp.domain.usecases.DeleteSavedUsecases
import com.example.newsapiapp.domain.usecases.GetNewsHeadLinesUsecases
import com.example.newsapiapp.domain.usecases.GetSavedNewsUsecases
import com.example.newsapiapp.domain.usecases.GetSearchNewsUsecases
import com.example.newsapiapp.domain.usecases.SaveNewsUsecases
import com.example.newsapiapp.presentation.viewModel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Provides
    fun provideNewsViewModelFactory(
        app: Application,
        getTopHeadLinesUsecases: GetNewsHeadLinesUsecases,
        getSearchNewsUsecases: GetSearchNewsUsecases,
        saveNewsUsecases: SaveNewsUsecases,
        getSavedNewsUsecase:GetSavedNewsUsecases,
        deleteSavedUsecases: DeleteSavedUsecases
        ): NewsViewModelFactory{
        return NewsViewModelFactory(app,getTopHeadLinesUsecases,getSearchNewsUsecases,saveNewsUsecases,getSavedNewsUsecase,deleteSavedUsecases)
    }
}