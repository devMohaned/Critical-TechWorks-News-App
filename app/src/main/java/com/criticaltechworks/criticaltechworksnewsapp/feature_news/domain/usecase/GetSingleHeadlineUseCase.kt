package com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase

import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.NewsDAO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSingleHeadlineUseCase(private val newsDAO: NewsDAO) {
    operator fun invoke(title: String, publishedAt: String): Flow<Resource<Article>> = flow {
        emit(Resource.Loading())
        try {
            val article =
                newsDAO.getSingleHeadlineByTitleAndPublish_CompositePrimaryKey(title, publishedAt)
                    .toArticle()
            emit(Resource.Success(article))
        }catch (noSuchElementException: NoSuchElementException){
            emit(Resource.Error(message = "Element is not found"))

        }

    }
}