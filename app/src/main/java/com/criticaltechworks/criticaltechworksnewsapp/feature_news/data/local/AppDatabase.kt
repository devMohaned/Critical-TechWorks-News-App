package com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.entity.ArticleEntity
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article

@Database(entities = [ArticleEntity::class], version = 1)
@TypeConverters(SourceEntityTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val newsDAO: NewsDAO
}