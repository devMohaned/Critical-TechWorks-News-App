package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto

data class HeadlineResponseDTO(
    val articles: List<ArticleDTO>,
    val status: String,
    val totalResults: Int
)