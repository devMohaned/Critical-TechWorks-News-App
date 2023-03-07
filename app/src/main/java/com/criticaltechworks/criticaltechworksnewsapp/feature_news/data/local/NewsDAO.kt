package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local

import androidx.room.*
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.ArticleEntity

@Dao
interface NewsDAO {

    /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeadline(article: ArticleEntity)

    @Query("SELECT * FROM articles")
    fun getAllHeadlines(): List<ArticleEntity>

    @Query("SELECT * FROM articles ORDER BY timeInMilliseconds ASC")
    fun getAllOrderedHeadlinesASC(): List<ArticleEntity>

    @Query("SELECT * FROM articles ORDER BY timeInMilliseconds DESC")
    fun getAllOrderedHeadlinesDES(): List<ArticleEntity>

    @Query("SELECT * FROM articles WHERE timeInMilliseconds BETWEEN :start AND :end ORDER BY timeInMilliseconds ASC")
    fun getHeadlinesWithinDate(start: Long, end: Long): List<ArticleEntity>

    @Query("SELECT * FROM articles ORDER BY timeInMilliseconds ASC LIMIT :startPos OFFSET :endPos")
    fun getHeadlinesFromAndTo(startPos: Int, endPos: Int): List<ArticleEntity>

    @Delete
    fun deleteHeadline(vararg article: ArticleEntity)

    @Delete
    fun deleteHeadlines(articles: List<ArticleEntity>)
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertHeadlines(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles WHERE source = :source ORDER BY timeInMilliseconds ASC LIMIT (:endPos - :startPos) OFFSET :startPos")
    fun getHeadlinesBySourceFromAndTo(source: String, startPos: Int, endPos: Int): List<ArticleEntity>


    @Query("SELECT * FROM articles WHERE title = :title AND publishedAt = :publishedAt")
    fun getSingleHeadlineByTitleAndPublish_CompositePrimaryKey(title: String, publishedAt: String) : ArticleEntity






}