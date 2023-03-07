package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_details

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source

data class NewsDetailState(
    val article: Article,
    val isLoading: Boolean
)