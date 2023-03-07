package com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.get_single_headline

import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.ArticleEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.SourceEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dao.FakeDAO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.GetSingleHeadlineUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetSingleHeadlineUseCaseTest {

    lateinit var fakeDAO: FakeDAO

    @Before
    fun setup() {
        fakeDAO = FakeDAO()
        val dummyArticles =
            listOf(
                ArticleEntity(
                    "A",
                    "content",
                    "desc",
                    "1/1/2023",
                    0L,
                    SourceEntity("bbc-news", "BBC News"),
                    "title", "url", "img"
                )
            )
        fakeDAO.insertHeadlines(dummyArticles)
    }

    @Test
    fun GetSingleHeadlineUseCase_ProvidedValidArguments_ShouldReturnSuccessResource() =
        runBlocking {
            val getSingleHeadlineUseCase = GetSingleHeadlineUseCase(fakeDAO)
            val result = getSingleHeadlineUseCase("title", "1/1/2023").toList()

            assertThat(result.first()).isInstanceOf(Resource.Loading::class.java)
            assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
            assertThat(result[1].data).isNotNull()
        }

    @Test
    fun GetSingleHeadlineUseCase_ProvidedWrongArguments_ShouldReturnSuccessResourceWithEmptyResult() =
        runBlocking {
            val getSingleHeadlineUseCase = GetSingleHeadlineUseCase(fakeDAO)
            val result = getSingleHeadlineUseCase("wrongPrimaryKeyOfTitle", "1/1/2023").toList()

            assertThat(result.first()).isInstanceOf(Resource.Loading::class.java)
            assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
            assertThat(result[1].data).isNull()
        }

}