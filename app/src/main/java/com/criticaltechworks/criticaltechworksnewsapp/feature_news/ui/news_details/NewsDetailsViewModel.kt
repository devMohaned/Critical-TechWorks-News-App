package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Sources
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.GetSingleHeadlineUseCase
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_list.NewsUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(private val getSingleHeadlineUseCase: GetSingleHeadlineUseCase) :
    ViewModel() {

    private val emptyArticle = Article(
        "", "", "", "",
        Sources[0], "", "", ""
    )
    private val _uiState = mutableStateOf(NewsDetailState(emptyArticle, false))
    val uiState: State<NewsDetailState> = _uiState

    fun fetchData(
        title: String,
        publishedAt: String,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = viewModelScope.launch(dispatcher) {
        getSingleHeadlineUseCase(
            title,
            publishedAt
        ).onEach {
            when (it) {
                // We only get success/loading case here, because of how we reach to this point, using the list
                // which also uses the same source of truth (database), thus Ignore Resource.Error
                is Resource.Error -> {
                    _uiState.value = uiState.value.copy(isLoading = false)
                }
                is Resource.Loading -> {
                    _uiState.value = uiState.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false, article = it.data ?: emptyArticle
                    )
                }
            }
        }.launchIn(this)
    }

}