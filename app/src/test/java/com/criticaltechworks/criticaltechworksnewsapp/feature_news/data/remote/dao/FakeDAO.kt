package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dao

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.NewsDAO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.ArticleEntity


class FakeDAO : NewsDAO {
    val list: MutableList<ArticleEntity> = mutableListOf()


    override fun insertHeadlines(articles: List<ArticleEntity>) {
        list.addAll(articles)
    }

    override fun getHeadlinesBySourceFromAndTo(
        source: String,
        startPos: Int,
        endPos: Int
    ): List<ArticleEntity> {
        return list.filter {
            it.source.id == source
        }.sortedBy {
            it.timeInMilliseconds
        }.filterIndexed { index, articleEntity ->
            run {
                index in startPos..endPos
            }
        }
    }

    override fun getSingleHeadlineByTitleAndPublish_CompositePrimaryKey(
        title: String,
        publishedAt: String
    ): ArticleEntity {
        return list.first {
            it.title == title && it.publishedAt == publishedAt
        }
    }

}