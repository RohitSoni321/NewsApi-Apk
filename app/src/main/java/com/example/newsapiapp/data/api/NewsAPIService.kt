package com.example.newsapiapp.data.api

import com.example.newsapiapp.BuildConfig
import com.example.newsapiapp.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadLines(
        @Query("country")
        country:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ):Response<APIResponse>

    @GET("/v2/top-headlines")
    suspend fun getTopSearchedHeadLines(
        @Query("country")
        country:String,
        @Query("q")
        searchQuery:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ):Response<APIResponse>
}