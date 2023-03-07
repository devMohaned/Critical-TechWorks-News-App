package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.api

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.NewsApi
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto.ArticleDTO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto.HeadlineResponseDTO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto.SourceDTO

class CorrectFakeApi : NewsApi {
    val correctSource = SourceDTO(id = "bbc-news", "BBC News")

    val correctArticles =
        (1..20).map { idx ->
            ArticleDTO(
                author = "Author $idx",
                content = "Content $idx",
                description = "Description $idx",
                publishedAt = "$idx/1/2023",
                source = correctSource,
                title = "Title $idx",
                url = "URL $idx",
                urlToImage = "IMAGE URL $idx"
            )
        }


    val correctResponse = HeadlineResponseDTO(
        articles = correctArticles,
        status = NewsApi.RESULT_FROM_SERVER.ok.name,
        totalResults = correctArticles.size
    )

    override suspend fun getAllHeadlines(source: String): HeadlineResponseDTO {
        return correctResponse
    }
}