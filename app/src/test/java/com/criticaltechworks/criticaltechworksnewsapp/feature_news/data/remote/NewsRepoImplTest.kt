package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote

import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.ArticleEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.SourceEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.api.CorrectFakeApi
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.api.InCorrectFakeApi
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.api.ThrowHTTPExceptionApi
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.api.ThrowIOExceptionApi
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dao.FakeDAO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.repo.NewsRepo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NewsRepoImplTest {

    private val CORRECT_SOURCE = "bbc-news"
    private lateinit var correctFakeApi: CorrectFakeApi
    private lateinit var inCorrectFakeApi: InCorrectFakeApi
    private lateinit var fakeDAO: FakeDAO

    @Before
    fun setup() {
        correctFakeApi = CorrectFakeApi()
        inCorrectFakeApi = InCorrectFakeApi()
        fakeDAO = FakeDAO()
    }

    @Test
    fun NewsRepo_FetchedCorrectResponseFromAPI_ShouldHaveResourceSuccess() = runBlocking {
        val newsRepo: NewsRepo = NewsRepoImpl(correctFakeApi, fakeDAO)

        val result = newsRepo.getHeadlinesFromAndTo(CORRECT_SOURCE, 0, 20).toList()

        assertThat(result.first()).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1].data).isEmpty()
        assertThat(result[1].data!!.size).isEqualTo(0)
        assertThat(result[2]).isInstanceOf(Resource.Success::class.java)
        assertThat(result[2].data).isNotEmpty()

    }

    @Test
    fun NewsRepo_FetchedCorrectResponseFromAPI_ShouldCacheResponseLocally() = runBlocking {
        val newsRepo: NewsRepo = NewsRepoImpl(correctFakeApi, fakeDAO)

        val result = newsRepo.getHeadlinesFromAndTo(CORRECT_SOURCE, 0, 20).toList()

        assertThat(result[2].data).isNotEmpty()
        assertThat(result[2].data!!.size).isEqualTo(correctFakeApi.correctArticles.size)
    }

    @Test
    fun NewsRepo_FetchedErrorResponseFromAPI_ShouldHaveResourceFail() = runBlocking {
        val newsRepo: NewsRepo = NewsRepoImpl(inCorrectFakeApi, fakeDAO)

        val result = newsRepo.getHeadlinesFromAndTo(CORRECT_SOURCE, 0, 20).toList()

        assertThat(result.first()).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1].data).isEmpty()
        assertThat(result[1].data!!.size).isEqualTo(0)
        assertThat(result[2]).isInstanceOf(Resource.Error::class.java)
        assertThat(result[3]).isInstanceOf(Resource.Success::class.java)
        assertThat(result[3].data).isEmpty()
    }

    @Test
    fun NewsRepo_FetchedErrorResponseFromAPI_ShouldHaveEmitOldStoredLocalData() = runBlocking {
        val newsRepo: NewsRepo = NewsRepoImpl(inCorrectFakeApi, fakeDAO)

        val result = newsRepo.getHeadlinesFromAndTo(CORRECT_SOURCE, 0, 20).toList()

        assertThat(result[2].data).isEmpty()
        assertThat(result[2].data!!.size).isEqualTo(0)
    }

    @Test
    fun NewsRepo_FetchedCorrectResponseFromAPI_ShouldHaveEmitOldStoredLocalData() = runBlocking {
        val newsRepo: NewsRepo = NewsRepoImpl(correctFakeApi, fakeDAO)

        // Mimic Old Stored Data
        val dummyArticles =
            listOf( ArticleEntity(
                "A",
                "content",
                "desc",
                "1/1/2023",
                0L,
                SourceEntity("INVALID_SOURCE", "src_name"),
                "title", "url", "img"
            ))
        fakeDAO.insertHeadlines( dummyArticles)
        // Mimic Old Stored Data


        val result = newsRepo.getHeadlinesFromAndTo("INVALID_SOURCE", 0, 20).toList()

        assertThat(result[2].data).isNotEmpty()
        assertThat(result[2].data!!.size).isEqualTo(1)
    }

    @Test
    fun NewsRepo_FetchedIOExceptionResponse_ShouldHaveEmitOldStoredLocalData() = runBlocking {
        val newsRepo: NewsRepo = NewsRepoImpl(ThrowIOExceptionApi(), fakeDAO)

        val result = newsRepo.getHeadlinesFromAndTo(CORRECT_SOURCE, 0, 20).toList()

        assertThat(result[2].data).isEmpty()
        assertThat(result[2].data!!.size).isEqualTo(0)
    }

    @Test
    fun NewsRepo_FetchedHTTPExceptionResponse_ShouldHaveEmitOldStoredLocalData() = runBlocking {
        val newsRepo: NewsRepo = NewsRepoImpl(ThrowHTTPExceptionApi(), fakeDAO)

        val result = newsRepo.getHeadlinesFromAndTo(CORRECT_SOURCE, 0, 20).toList()

        assertThat(result[2].data).isEmpty()
        assertThat(result[2].data!!.size).isEqualTo(0)
    }

}



