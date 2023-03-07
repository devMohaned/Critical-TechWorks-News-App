package com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase

import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.repo.NewsRepo
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(private val newsRepo: NewsRepo) {
    operator fun invoke(source: String, startPos: Int, endPos: Int): Flow<Resource<List<Article>>>{
        return newsRepo.getHeadlinesFromAndTo(source, startPos, endPos)
    }
}