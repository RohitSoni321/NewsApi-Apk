package com.example.newsapiapp.data.repository.newsDataSourceImpl

import com.example.newsapiapp.data.api.NewsAPIService
import com.example.newsapiapp.data.model.APIResponse
import com.example.newsapiapp.data.repository.newsDataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsAPIService: NewsAPIService): NewsRemoteDataSource {
    override suspend fun getTopHeadLines(country:String,page:Int): Response<APIResponse> {
        return newsAPIService.getTopHeadLines(country,page)
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse> {
        return newsAPIService.getTopSearchedHeadLines(country,searchQuery,page)
    }
}