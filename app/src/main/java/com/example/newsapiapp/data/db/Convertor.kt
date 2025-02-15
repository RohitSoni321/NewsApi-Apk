package com.example.newsapiapp.data.db

import androidx.room.TypeConverter
import com.example.newsapiapp.data.model.Source

class Convertor {
    @TypeConverter
    fun fromSource(source:Source):String{
        return source.name.toString()
    }
    @TypeConverter
    fun toSource(name:String): Source{
        return Source(name,name)
    }
}