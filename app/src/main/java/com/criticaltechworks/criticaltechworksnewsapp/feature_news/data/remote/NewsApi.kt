package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto.ArticleDTO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto.HeadlineResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    enum class RESULT_FROM_SERVER
    {
        ok,error
    }

    companion object{
        val BASE_URL = "https://newsapi.org/"

    }

    @GET("v2/top-headlines?apiKey=1691bcfd1aab426c9c503cf50d6b9412")
    suspend fun getAllHeadlines(@Query("sources") source: String): HeadlineResponseDTO // source = bbc-news
}