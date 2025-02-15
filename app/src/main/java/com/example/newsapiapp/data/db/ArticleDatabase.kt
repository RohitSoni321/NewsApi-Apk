package com.example.newsapiapp.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapiapp.data.model.Article
import com.example.newsapiapp.data.db.Convertor


@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Convertor::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}