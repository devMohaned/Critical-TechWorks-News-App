package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_list

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source

data class NewsUIState(
    val isLoading: Boolean,
    val newsList: List<Article>,
    val source: Source,
    val startPos: Int,
    val endPos: Int
) {
}

sealed class NewsUIEvent {
    object ShowDialog : NewsUIEvent()
    class ShowSnackBar(val message: String) : NewsUIEvent()
}