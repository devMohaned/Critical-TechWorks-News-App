package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote

import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.NewsDAO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.repo.NewsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepoImpl(private val newsApi: NewsApi, private val newsDAO: NewsDAO) : NewsRepo {
    override fun getHeadlinesFromAndTo(
        source: String,
        startPos: Int,
        endPos: Int
    ): Flow<Resource<List<Article>>> =
        flow {
            emit(Resource.Loading())


            val oldDataEntity = newsDAO.getHeadlinesBySourceFromAndTo(source, startPos, endPos)
            val oldData = oldDataEntity.map { it.toArticle() }
            emit(Resource.Loading(data = oldData))

            try {

                val result = newsApi.getAllHeadlines(source)
                if (result.status == NewsApi.RESULT_FROM_SERVER.ok.name) {
                    val newData = result.articles.map { it.toArticleEntity() }
                    newsDAO.insertHeadlines(newData)
                } else if (result.status == NewsApi.RESULT_FROM_SERVER.error.name) {
                    emit(
                        Resource.Error(
                            message = "Error Response from server",
                            data = oldData
                        )
                    )
                }

            } catch (ioException: IOException) {
                emit(
                    Resource.Error(
                        message = "Could not reach the server, check your internet connection",
                        data = oldData
                    )
                )
            } catch (httpException: HttpException) {
                emit(
                    Resource.Error(
                        message = "Ops Something Went Wrong, Could not reach the server, try again later",
                        data = oldData
                    )
                )
            }

            val data = newsDAO.getHeadlinesBySourceFromAndTo(
                source,
                startPos,
                endPos
            ).map { it.toArticle() }

            emit(Resource.Success(data = data))

        }
}