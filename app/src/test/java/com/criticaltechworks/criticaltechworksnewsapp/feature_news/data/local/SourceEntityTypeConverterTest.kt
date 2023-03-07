package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.SourceEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class SourceEntityTypeConverterTest {

    lateinit var converter: SourceEntityTypeConverter
    @Before
    fun setup() {
         converter = SourceEntityTypeConverter()
    }

    @Test
    fun SourceEntityTypeConverter_PassedSourceEntity_ShouldReturnSourceId() {
        val source = SourceEntity("src-id", "src name")
        val result = converter.fromTimestamp(source)
        assertThat(result).isEqualTo("src-id")
    }

    @Test
    fun SourceEntityTypeConverter_PassedStringOfSourceId_ShouldReturnSourceWithThatStringProvided() {
        val result = converter.dateToTimestamp("src-id")
        assertThat(result).isInstanceOf(SourceEntity::class.java)
        assertThat(result.id).isEqualTo("src-id")
    }

}