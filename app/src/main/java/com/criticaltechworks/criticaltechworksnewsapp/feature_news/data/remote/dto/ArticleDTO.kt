package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto

import com.criticaltechworks.criticaltechworksnewsapp.core.util.beautifyDate
import com.criticaltechworks.criticaltechworksnewsapp.core.util.convertToDate
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.ArticleEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article

data class ArticleDTO(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: SourceDTO,
    val title: String,
    val url: String?,
    val urlToImage: String?
)

{
    fun toArticle(): Article{
        return Article(author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            source = source.toSource(),
            title = title,
            url = url,
            urlToImage = urlToImage)
    }

    fun toArticleEntity(): ArticleEntity{
        return ArticleEntity(author = author,
            content = content,
            description = description,
            publishedAt = publishedAt.beautifyDate(),
            timeInMilliseconds = publishedAt.convertToDate(),
            source = source.toSourceEntity(),
            title = title,
            url = url,
            urlToImage = urlToImage)
    }
}