package com.example.newsapiapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapiapp.domain.usecases.DeleteSavedUsecases
import com.example.newsapiapp.domain.usecases.GetNewsHeadLinesUsecases
import com.example.newsapiapp.domain.usecases.GetSavedNewsUsecases
import com.example.newsapiapp.domain.usecases.GetSearchNewsUsecases
import com.example.newsapiapp.domain.usecases.SaveNewsUsecases

class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadLinesUsecases: GetNewsHeadLinesUsecases,
    private val getSearchNewsUsecases: GetSearchNewsUsecases,
    private val saveNewsUsecases: SaveNewsUsecases,
    private val getSaveNewsUsecases: GetSavedNewsUsecases,
    private val deleteSavedUsecases: DeleteSavedUsecases
):
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(app,getNewsHeadLinesUsecases,getSearchNewsUsecases,saveNewsUsecases,getSaveNewsUsecases,deleteSavedUsecases) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}