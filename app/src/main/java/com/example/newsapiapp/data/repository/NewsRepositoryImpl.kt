package com.example.newsapiapp.data.repository

import com.example.newsapiapp.data.model.APIResponse
import com.example.newsapiapp.data.model.Article
import com.example.newsapiapp.data.repository.newsDataSource.NewsLocalDataSource
import com.example.newsapiapp.data.repository.newsDataSource.NewsRemoteDataSource
import com.example.newsapiapp.data.utils.Resource
import com.example.newsapiapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource): NewsRepository {
    override suspend fun getNewsHeadLines(country:String,page:Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadLines(country,page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedNews(country,searchQuery,page))
    }

    private fun responseToResource(response:Response<APIResponse>): Resource<APIResponse>{
        if(response.isSuccessful){
            response.body()?.let {results ->
                return Resource.Success(results)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun deleteSavedNews(article: Article) {
        newsLocalDataSource.deleteSavedArticleFromDB(article)
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticle()
    }
}