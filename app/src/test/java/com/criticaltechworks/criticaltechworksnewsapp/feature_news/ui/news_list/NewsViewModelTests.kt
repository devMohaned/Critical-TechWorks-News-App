package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_list

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.core.MainDispatcherRule
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.GetNewsUseCase
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.InValidErrorFakeNewsRepo
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.ValidSuccessFakeNewsRepo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsViewModelTests {

    lateinit var validSuccessGetNewsUseCase: GetNewsUseCase
    lateinit var inValidErrorGetNewsUseCase: GetNewsUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        val validSuccessFakeNewsRepo = ValidSuccessFakeNewsRepo()
        val inValidErrorFakeNewsRepo = InValidErrorFakeNewsRepo()

        validSuccessGetNewsUseCase = GetNewsUseCase(validSuccessFakeNewsRepo)
        inValidErrorGetNewsUseCase = GetNewsUseCase(inValidErrorFakeNewsRepo)
    }

    @Test
    fun NewsViewModel_UIStateType_ShouldBeNewsUIStateWithLoadingData() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        assertThat(newsViewModel.uiState.value).isInstanceOf(NewsUIState::class.java)
        assertThat(newsViewModel.uiState.value.isLoading).isTrue()
    }

    @Test
    fun NewsViewModel_UIEventType_ShouldBeEmptyNewsUIEvent() = runBlocking {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)

        val testResults = mutableListOf<NewsUIEvent>()
        val job = launch {
            newsViewModel.uiEvents.toList(testResults)
        }

        assertThat(testResults.size).isEqualTo(0)
        job.cancel()
    }

    @Test
    fun NewsViewModel_SourceIsBlank_ShouldReturnFalse() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val result = newsViewModel.validateSource("       ")
        assertThat(result).isFalse()
    }

    @Test
    fun NewsViewModel_SourceIsEmpty_ShouldReturnFalse() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val result = newsViewModel.validateSource("")
        assertThat(result).isFalse()
    }

    @Test
    fun NewsViewModel_SourceIsCorrect_ShouldReturnTrue() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val result = newsViewModel.validateSource("anySourceNotBlankOrEmpty")
        assertThat(result).isTrue()
    }

    @Test
    fun NewsViewModel_StartPositionIsNegative_ShouldReturnFalse() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val result = newsViewModel.isValidPosition(-1, 20)
        assertThat(result).isFalse()
    }

    @Test
    fun NewsViewModel_EndPositionIsNegative_ShouldReturnFalse() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val result = newsViewModel.isValidPosition(0, -20)
        assertThat(result).isFalse()
    }

    @Test
    fun NewsViewModel_StartPositionIsGreaterThanEndPosition_ShouldReturnFalse() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val result = newsViewModel.isValidPosition(20, 0)
        assertThat(result).isFalse()
    }

    @Test
    fun NewsViewModel_StartPositionIsSmallerThanEndPosition_ShouldReturnTrue() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val result = newsViewModel.isValidPosition(0, 20)
        assertThat(result).isTrue()
    }

    @Test
    fun NewsViewModel_StartPositionIsSameAsEndPosition_ShouldReturnTrue() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val result = newsViewModel.isValidPosition(1, 1)
        assertThat(result).isFalse()
    }

    @Test
    fun NewsViewModel_UpdateNewsSource_ShouldUpdateUIStateWithNewSource() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val newSource = Source("src-id", "src name")
        newsViewModel.updateNewsSource(newSource)
        assertThat(newsViewModel.uiState.value.source).isEqualTo(newSource)
    }

    @Test
    fun NewsViewModel_UpdateSelectedArticleForLargerScreens_ShouldChangeSelectedArticleValueToArgumentPassed() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val newSource = Source("src-id", "src name")
        val article = Article(
            "author", "content", "desc", "1/1/2023",
            newSource, "Title", null, null
        )
        newsViewModel.updateSelectedArticle(article)
        assertThat(newsViewModel.selectedArticle.value).isEqualTo(article)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun NewsViewModel_showDialog_ShouldEmitNewsUIEventOfTypeShowDialog() = runTest {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val values = mutableListOf<NewsUIEvent>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            newsViewModel.uiEvents.toList(values)
        }
        newsViewModel.showDialog()
        assertThat(values.last()).isSameInstanceAs(NewsUIEvent.ShowDialog)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun NewsViewModel_showSnackBar_ShouldEmitNewsUIEventOfTypeShowSnackBar() = runTest {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        val values = mutableListOf<NewsUIEvent>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            newsViewModel.uiEvents.toList(values)
        }
        newsViewModel.showSnackBar("dummy_message")
        assertThat(values.last()).isInstanceOf(NewsUIEvent.ShowSnackBar::class.java)
    }

    @Test
    fun NewsViewModel_updateDialogVisibility_ShouldReplaceOldDataWithOppositeData() {
        val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
        var result = newsViewModel.isDialogVisibleUIState.value
        assertThat(result).isFalse()
        newsViewModel.updateDialogVisibility(true)
        result = newsViewModel.isDialogVisibleUIState.value
        assertThat(result).isTrue()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun NewsViewModel_SearchSubmittedWithValidGetNewsUseCase_ShouldExpectSuccessResourceWithNewList() =
        runTest {
            val newsViewModel = NewsViewModel(validSuccessGetNewsUseCase)
            assertThat(newsViewModel.uiState.value.newsList).isEmpty()
            assertThat(newsViewModel.uiState.value.newsList.size).isEqualTo(0)
            newsViewModel.onSearchSubmit(dispatcher = UnconfinedTestDispatcher(testScheduler))
            assertThat(newsViewModel.uiState.value.newsList).isNotEmpty()
            assertThat(newsViewModel.uiState.value.newsList.size).isGreaterThan(0)
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun NewsViewModel_SearchSubmittedWithInvalidGetNewsUseCase_ShouldExpectNoChangeWithNewList() =
        runTest {
            val newsViewModel = NewsViewModel(inValidErrorGetNewsUseCase)
            assertThat(newsViewModel.uiState.value.newsList).isEmpty()
            assertThat(newsViewModel.uiState.value.newsList.size).isEqualTo(0)
            assertThat(newsViewModel.uiState.value.isLoading).isTrue()
            newsViewModel.onSearchSubmit(dispatcher = UnconfinedTestDispatcher(testScheduler))
            assertThat(newsViewModel.uiState.value.newsList).isEmpty()
            assertThat(newsViewModel.uiState.value.newsList.size).isEqualTo(0)
            assertThat(newsViewModel.uiState.value.isLoading).isFalse()
        }
}