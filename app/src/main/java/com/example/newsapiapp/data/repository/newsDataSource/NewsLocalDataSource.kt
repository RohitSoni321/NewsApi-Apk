package com.example.newsapiapp.data.repository.newsDataSource

import com.example.newsapiapp.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article);
    fun getSavedArticle():Flow<List<Article>>
    suspend fun deleteSavedArticleFromDB(article: Article);
}