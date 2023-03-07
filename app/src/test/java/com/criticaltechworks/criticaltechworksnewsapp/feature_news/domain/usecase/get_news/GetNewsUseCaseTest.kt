package com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.get_news

import com.criticaltechworks.criticaltechworksnewsapp.core.util.Resource
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.GetNewsUseCase
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.InValidErrorFakeNewsRepo
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.ValidSuccessFakeNewsRepo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetNewsUseCaseTest {

    @Test
    fun GetNewsUseCase_ValidGetNewsUseCase_ShouldEmitSuccessResource() = runBlocking{
        val getNewsUseCase = GetNewsUseCase(ValidSuccessFakeNewsRepo())
        val result = getNewsUseCase("",0,20).toList()

        assertThat(result.last()).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun GetNewsUseCase_InValidGetNewsUseCase_ShouldEmitErrorResource() = runBlocking{
        val getNewsUseCase = GetNewsUseCase(InValidErrorFakeNewsRepo())
        val result = getNewsUseCase("",0,20).toList()

        assertThat(result.last()).isInstanceOf(Resource.Error::class.java)
    }
}


