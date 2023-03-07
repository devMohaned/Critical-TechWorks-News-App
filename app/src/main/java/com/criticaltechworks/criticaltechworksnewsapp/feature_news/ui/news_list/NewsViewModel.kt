package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.criticaltechworks.criticaltechworksnewsapp.BuildConfig
import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Sources
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {

    private val _uiState = mutableStateOf(
        NewsUIState(
            true,
            emptyList(),
            Source(BuildConfig.SOURCE_ID, BuildConfig.SOURCE_NAME),
            0,
            20
        )
    )
    val uiState: State<NewsUIState> = _uiState

    private val _uiEvents = MutableSharedFlow<NewsUIEvent>()
    val uiEvents: SharedFlow<NewsUIEvent> = _uiEvents.asSharedFlow()

    private val _isDialogVisibleUIState: MutableState<Boolean> = mutableStateOf(false)
    val isDialogVisibleUIState = _isDialogVisibleUIState

    private val _selectedArticle =
        mutableStateOf<Article>(
            Article(
                null, null,
                null, "Click on a headline to view its details", Sources[0],
                "Headline", null,
                null
            )
        )
    val selectedArticle = _selectedArticle

    fun onSearchSubmit(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        viewModelScope.launch(dispatcher) {
            if (validateSource(_uiState.value.source.id)
                && isValidPosition(_uiState.value.startPos, _uiState.value.endPos)
            ) {
                getNewsUseCase(
                    _uiState.value.source.id,
                    _uiState.value.startPos,
                    _uiState.value.endPos
                )
                    .onEach {
                        when (it) {
                            is Resource.Loading -> {
                                _uiState.value = uiState.value.copy(
                                    newsList = it.data ?: emptyList(),
                                    isLoading = true
                                )
                            }
                            is Resource.Error -> {
                                _uiState.value = uiState.value.copy(
                                    newsList = it.data ?: emptyList(),
                                    isLoading = false
                                )
                                showSnackBar(it.message ?: "Unknown Error")
                            }
                            is Resource.Success -> {
                                _uiState.value = uiState.value.copy(
                                    newsList = it.data ?: emptyList(),
                                    isLoading = false
                                )
                            }
                        }
                    }.launchIn(this)

            }

        }

    fun validateSource(source: String): Boolean {
        if (source.isBlank() || source.isEmpty())
            return false

        return true
    }

    fun isValidPosition(startPos: Int, endPos: Int): Boolean {
        if (startPos < 0 || endPos < 0)
            return false

        if (startPos < endPos)
            return true

        return false
    }

    fun updateNewsSource(source: Source) {
        _uiState.value = uiState.value.copy(
            source = source
        )
    }

    fun showDialog() = viewModelScope.launch {
        _uiEvents.emit(NewsUIEvent.ShowDialog)
    }

    fun showSnackBar(message: String) = viewModelScope.launch {
        _uiEvents.emit(NewsUIEvent.ShowSnackBar(message))
    }

    fun updateDialogVisibility(isDialogVisible: Boolean) {
        _isDialogVisibleUIState.value = isDialogVisible
    }

    fun updateSelectedArticle(article: Article) {
        _selectedArticle.value = article
    }

}