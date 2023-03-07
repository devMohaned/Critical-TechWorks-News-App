package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.api

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.NewsApi
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto.HeadlineResponseDTO


class InCorrectFakeApi : NewsApi {

    val inCorrectResponse = HeadlineResponseDTO(
        articles = emptyList(),
        status = NewsApi.RESULT_FROM_SERVER.error.name,
        totalResults = 0
    )

    override suspend fun getAllHeadlines(source: String): HeadlineResponseDTO {
        return inCorrectResponse
    }
}