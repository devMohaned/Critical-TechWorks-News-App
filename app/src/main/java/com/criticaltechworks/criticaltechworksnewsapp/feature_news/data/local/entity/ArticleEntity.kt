package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity

import androidx.room.Entity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article

@Entity(tableName = "articles", primaryKeys = ["title", "publishedAt"])
data class ArticleEntity(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val timeInMilliseconds: Long,
    val source: SourceEntity,
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
}