package com.example.newsapiapp.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {
    private lateinit var server: MockWebServer
    private lateinit var service: NewsAPIService

    @Before
    fun setUp(){
        server = MockWebServer()
        service = Retrofit.Builder().baseUrl(server.url("")).addConverterFactory(
            GsonConverterFactory.create()).build().create(NewsAPIService::class.java)
    }

    @After
    fun tearDown(){
        server.shutdown()
    }

    private fun enqueueMockResponse(fileName:String){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadLines_sentRequest_receivedExpected() {
        runBlocking{
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadLines(country = "us",page = 1)
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=4b19406e52174204b4533f1a2d9c3458")
        }
    }

    @Test
    fun getTopHeadLines_receivedResponse_correctPageSize() {
        runBlocking{
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadLines(country = "us",page = 1).body()
            val aritcleList = responseBody!!.articles
            assertThat(aritcleList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadLines_receivedResponse_correctContent() {
        runBlocking{
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadLines(country = "us",page = 1).body()
            val aritcleList = responseBody!!.articles
            val article = aritcleList[0]
            assertThat(article.author).isEqualTo("Fred Imbert")
            assertThat(article.url).isEqualTo("https://www.cnbc.com/2025/02/05/on-again-off-again-tariffs-are-having-consequences-even-as-sp-500-holds-up-for-now.html")
        }
    }

}