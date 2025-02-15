package com.example.newsapiapp.domain.usecases

import com.example.newsapiapp.data.model.APIResponse
import com.example.newsapiapp.data.utils.Resource
import com.example.newsapiapp.domain.repository.NewsRepository

class GetNewsHeadLinesUsecases(private val newsRepository: NewsRepository) {
    suspend fun execute(country:String,page:Int): Resource<APIResponse>{
        return newsRepository.getNewsHeadLines(country,page)
    }
}