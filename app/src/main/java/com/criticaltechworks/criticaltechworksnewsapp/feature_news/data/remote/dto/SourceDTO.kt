package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.dto

import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.SourceEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source

data class SourceDTO(
    val id: String,
    val name: String
)

{
    fun toSource(): Source{
        return Source(id = id, name = name)
    }

    fun toSourceEntity(): SourceEntity {
        return SourceEntity(id = id, name = name)
    }
}