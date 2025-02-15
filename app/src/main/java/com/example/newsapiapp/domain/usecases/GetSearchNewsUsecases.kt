package com.example.newsapiapp.domain.usecases

import com.example.newsapiapp.data.model.APIResponse
import com.example.newsapiapp.data.model.Article
import com.example.newsapiapp.data.utils.Resource
import com.example.newsapiapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSearchNewsUsecases(private val newsRepository: NewsRepository) {
    suspend fun execute(country:String,searchQuery:String,page:Int): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country,searchQuery,page)
    }
}