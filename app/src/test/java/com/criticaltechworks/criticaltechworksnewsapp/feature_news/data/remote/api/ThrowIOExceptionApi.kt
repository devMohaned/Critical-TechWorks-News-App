package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.api

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.NewsApi
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto.HeadlineResponseDTO
import java.io.IOException


class ThrowIOExceptionApi : NewsApi {

    override suspend fun getAllHeadlines(source: String): HeadlineResponseDTO {
        throw IOException()
    }
}