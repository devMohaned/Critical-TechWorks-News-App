package com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.repo

import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface NewsRepo {

    @GET("/v2/top-headlines?sources=bbc-news&apiKey=1691bcfd1aab426c9c503cf50d6b9412")
    fun getHeadlinesFromAndTo(source: String, startPos: Int, endPos: Int): Flow<Resource<List<Article>>>
}