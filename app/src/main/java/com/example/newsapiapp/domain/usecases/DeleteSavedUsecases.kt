package com.example.newsapiapp.domain.usecases

import com.example.newsapiapp.data.model.Article
import com.example.newsapiapp.domain.repository.NewsRepository


class DeleteSavedUsecases(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article){
        newsRepository.deleteSavedNews(article)
    }

}