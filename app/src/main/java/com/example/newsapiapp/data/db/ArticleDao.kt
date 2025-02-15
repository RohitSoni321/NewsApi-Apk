package com.example.newsapiapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapiapp.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert
    suspend fun saveArticle(article:Article)
    @Query("SELECT * FROM articles")
    fun getSavedArticles():Flow<List<Article>>
    @Delete
    suspend fun deleteArticle(article: Article)
}