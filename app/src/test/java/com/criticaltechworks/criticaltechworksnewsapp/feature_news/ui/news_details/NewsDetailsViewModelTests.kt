package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_details

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.core.MainDispatcherRule
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.ArticleEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.SourceEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dao.FakeDAO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.GetSingleHeadlineUseCase
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsDetailsViewModelTests {
    lateinit var getSingleHeadLineUseCase: GetSingleHeadlineUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        val fakeDAO = FakeDAO()
        val listOfArticleEntity = mutableListOf<ArticleEntity>()
        (1..20).forEach {
            listOfArticleEntity.add(
                ArticleEntity(
                    "a",
                    "c",
                    "d",
                    "$it/1/2023",
                    0L,
                    SourceEntity("src-id", "src-name"),
                    "title $it",
                    "",
                    ""
                )
            )
        }
        fakeDAO.insertHeadlines(
            listOfArticleEntity
        )
        getSingleHeadLineUseCase = GetSingleHeadlineUseCase(fakeDAO)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun NewsDetailsViewModel_FetchValidDataByNewsDetailsUseCase_ShouldReturnUpdatedArticle() =
        runTest {
            val newsDetailsViewModel = NewsDetailsViewModel(getSingleHeadLineUseCase)
            assertThat(newsDetailsViewModel.uiState.value.article).isNotNull()
            assertThat(newsDetailsViewModel.uiState.value.article.title).isNotEqualTo("title 5")

            newsDetailsViewModel.fetchData(
                title = "title 5",
                publishedAt = "5/1/2023",
                dispatcher = UnconfinedTestDispatcher(testScheduler)
            )
            assertThat(newsDetailsViewModel.uiState.value.article.title).isEqualTo("title 5")
        }




    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun NewsDetailsViewModel_FetchInValidDataByNewsDetailsUseCase_ShouldReturnTheSameArticleAsBeforeFetching() =
        runTest {
            val newsDetailsViewModel = NewsDetailsViewModel(getSingleHeadLineUseCase)
            assertThat(newsDetailsViewModel.uiState.value.article).isNotNull()
            assertThat(newsDetailsViewModel.uiState.value.article.title).isEqualTo("")

            newsDetailsViewModel.fetchData(
                title = "wrongTitle",
                publishedAt = "DoesNotExist",
                dispatcher = UnconfinedTestDispatcher(testScheduler)
            )
            assertThat(newsDetailsViewModel.uiState.value.article.title).isEqualTo("")
        }
}