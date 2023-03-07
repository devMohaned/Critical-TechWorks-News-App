package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.SourceEntity

@ProvidedTypeConverter
class SourceEntityTypeConverter {

        @TypeConverter
        fun fromTimestamp(sourceEntity: SourceEntity): String {
            return sourceEntity.id
        }

        @TypeConverter
        fun dateToTimestamp(sourceStr: String): SourceEntity {
            return SourceEntity(sourceStr, sourceStr.replace('-', ' '))
        }

}