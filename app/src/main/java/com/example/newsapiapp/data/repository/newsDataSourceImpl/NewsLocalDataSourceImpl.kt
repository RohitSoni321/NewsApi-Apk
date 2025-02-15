package com.example.newsapiapp.data.repository.newsDataSourceImpl

import com.example.newsapiapp.data.db.ArticleDao
import com.example.newsapiapp.data.model.Article
import com.example.newsapiapp.data.repository.newsDataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val articleDao:ArticleDao): NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDao.saveArticle(article)
    }

    override fun getSavedArticle():Flow<List<Article>>{
        return articleDao.getSavedArticles()
    }

    override suspend fun deleteSavedArticleFromDB(article: Article) {
        articleDao.deleteArticle(article)
    }
}