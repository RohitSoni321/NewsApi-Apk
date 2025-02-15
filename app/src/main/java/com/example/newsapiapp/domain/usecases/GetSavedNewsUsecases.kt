package com.example.newsapiapp.domain.usecases

import com.example.newsapiapp.data.model.APIResponse
import com.example.newsapiapp.data.model.Article
import com.example.newsapiapp.data.utils.Resource
import com.example.newsapiapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUsecases(private val newsRepository: NewsRepository) {
   fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }
}