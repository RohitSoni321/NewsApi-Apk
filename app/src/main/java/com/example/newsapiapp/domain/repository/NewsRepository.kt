package com.example.newsapiapp.domain.repository

import com.example.newsapiapp.data.model.APIResponse
import com.example.newsapiapp.data.model.Article
import com.example.newsapiapp.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadLines(country:String,page:Int): Resource<APIResponse>
    suspend fun getSearchedNews(country: String,searchQuery:String,page:Int):Resource<APIResponse>
    suspend fun deleteSavedNews(article: Article)
    suspend fun saveNews(article: Article)
     fun getSavedNews(): Flow<List<Article>>
}