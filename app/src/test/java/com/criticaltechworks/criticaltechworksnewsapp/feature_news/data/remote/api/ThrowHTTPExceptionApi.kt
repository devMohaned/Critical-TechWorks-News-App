package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.api

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.NewsApi
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto.HeadlineResponseDTO
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class ThrowHTTPExceptionApi : NewsApi {

    override suspend fun getAllHeadlines(source: String): HeadlineResponseDTO {
        val response: Response<String> = Response.success("UselessData")
        throw HttpException(response)
    }
}