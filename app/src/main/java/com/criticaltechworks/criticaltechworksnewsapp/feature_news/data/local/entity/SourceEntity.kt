package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source

data class SourceEntity(
    val id: String,
    val name: String
)

{
    fun toSource(): Source{
        return Source(id = id, name = name)
    }
}