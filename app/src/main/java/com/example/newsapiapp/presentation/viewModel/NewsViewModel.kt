package com.example.newsapiapp.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsapiapp.data.model.APIResponse
import com.example.newsapiapp.data.model.Article
import com.example.newsapiapp.data.utils.Resource
import com.example.newsapiapp.domain.usecases.DeleteSavedUsecases
import com.example.newsapiapp.domain.usecases.GetNewsHeadLinesUsecases
import com.example.newsapiapp.domain.usecases.GetSavedNewsUsecases
import com.example.newsapiapp.domain.usecases.GetSearchNewsUsecases
import com.example.newsapiapp.domain.usecases.SaveNewsUsecases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadLinesUsecases: GetNewsHeadLinesUsecases,
    private val getSearchNewsUsecases: GetSearchNewsUsecases,
    private val saveNewsUsecases: SaveNewsUsecases,
    private val getSaveNewsUsecases: GetSavedNewsUsecases,
    private val deleteSavedUsecases: DeleteSavedUsecases
): AndroidViewModel(app) {
    var newsHeadLines:MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    fun getTopHeadLines(country:String,page:Int){
        viewModelScope.launch(Dispatchers.IO) {
            newsHeadLines.postValue(Resource.Loading())
            try {
                if (isInternetAvailable(app)) {
                    val apiResult = getNewsHeadLinesUsecases.execute(country, page)
                    newsHeadLines.postValue(apiResult)
                } else {
                    newsHeadLines.postValue(Resource.Error("Internet is not available"))
                }
            } catch (e: Exception) {
                newsHeadLines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }
     var searchedNewsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    fun getSearchedNewsHeadlines(country:String,searchQuery:String,page:Int){
        viewModelScope.launch(Dispatchers.IO) {
            searchedNewsHeadlines.postValue(Resource.Loading())
            try {
                if (isInternetAvailable(app)) {
                    val apiResult = getSearchNewsUsecases.execute(country,searchQuery, page)
                    searchedNewsHeadlines.postValue(apiResult)
                } else {
                    searchedNewsHeadlines.postValue(Resource.Error("Internet is not available"))
                }
            } catch (e: Exception) {
                searchedNewsHeadlines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }



    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }


    fun saveArticle(article: Article) = viewModelScope.launch{
        Log.d("MYTAG","i am saving the news")
        saveNewsUsecases.execute(article)
    }
    fun getSavedNews() = liveData{
        getSaveNewsUsecases.execute().collect{
            emit(it)
        }
    }
    fun deleteSaveNews(article: Article){
        viewModelScope.launch{
            deleteSavedUsecases.execute(article)
        }

    }

}