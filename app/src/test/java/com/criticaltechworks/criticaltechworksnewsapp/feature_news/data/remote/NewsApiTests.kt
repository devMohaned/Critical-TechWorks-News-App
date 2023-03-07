package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NewsApiTests {

    @Test
    fun NewsApi_BaseUrlIsCorrect_ShouldReturnNewsApiWebsite() {
        assertThat(NewsApi.BASE_URL).isEqualTo("https://newsapi.org/")
    }
}