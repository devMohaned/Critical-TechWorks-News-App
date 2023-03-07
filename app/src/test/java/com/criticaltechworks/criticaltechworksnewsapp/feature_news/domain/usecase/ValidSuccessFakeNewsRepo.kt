package com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase

import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.repo.NewsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ValidSuccessFakeNewsRepo : NewsRepo {
    override fun getHeadlinesFromAndTo(
        source: String,
        startPos: Int,
        endPos: Int
    ): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Success<List<Article>>(listOf(Article("","","","1/1/2023", Source("src-id","src-name"),"title","",""))))
    }
}
