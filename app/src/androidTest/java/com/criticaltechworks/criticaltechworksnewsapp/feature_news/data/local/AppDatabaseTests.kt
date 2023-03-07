package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.ArticleEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.SourceEntity
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

// It's recommended to use Instrumentation tests as unit tests for Room Database
@RunWith(AndroidJUnit4::class)
class AppDatabaseTests {
    lateinit var newsDao: NewsDAO
    lateinit var db: AppDatabase

    val dummyArticles =
        listOf(
            ArticleEntity(
                "A",
                "content",
                "desc",
                "1/1/2023",
                0L,
                SourceEntity("src-id", "src-name"),
                "title", "url", "img"
            ),
        )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).addTypeConverter(SourceEntityTypeConverter()).allowMainThreadQueries().build()
        newsDao = db.newsDAO
    }

    @After
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun AppDatabase_InsertHeadlines_ShouldStoreHeadlinesLocallyInRoomDatabase() {

        var resultList = newsDao.getHeadlinesBySourceFromAndTo("src-id", 0, 20)
        assertThat(resultList).isEmpty()
        newsDao.insertHeadlines(dummyArticles)
        resultList = newsDao.getHeadlinesBySourceFromAndTo("src-id", 0, 20)
        assertThat(resultList).isNotEmpty()
        assertThat(resultList.size).isEqualTo(1)
    }

    @Test
    fun AppDatabase_GetValidArticleEntityByCompositePrimaryKey_ShouldRetrieveHeadlinesFromLocalRoomDatabase() {
        newsDao.insertHeadlines(dummyArticles)
        val result =
            newsDao.getSingleHeadlineByTitleAndPublish_CompositePrimaryKey("title", "1/1/2023")
        assertThat(result).isInstanceOf(ArticleEntity::class.java)
        assertThat(result).isNotNull()
    }

    @Test
    fun AppDatabase_GetEmptyArticleEntityByCompositePrimaryKey_ShouldRetrieveHeadlinesFromLocalRoomDatabase() {
        val result =
            newsDao.getSingleHeadlineByTitleAndPublish_CompositePrimaryKey("wrongPrimaryKeyTitle", "1/1/2023")
        assertThat(result).isNull()
    }
}