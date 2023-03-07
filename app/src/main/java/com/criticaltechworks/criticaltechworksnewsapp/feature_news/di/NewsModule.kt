package com.criticaltechworks.criticaltechworksnewsapp.feature_news.di

import android.content.Context
import androidx.room.Room
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.AppDatabase
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.NewsDAO
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.local.SourceEntityTypeConverter
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.NewsApi
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.data.remote.NewsRepoImpl
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.repo.NewsRepo
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.GetNewsUseCase
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.usecase.GetSingleHeadlineUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "critical-techworkx-news-database"
        )  .addTypeConverter(SourceEntityTypeConverter())
            .build()
    }

    @Provides
    fun providesNewsDAO(database: AppDatabase): NewsDAO {
        return database.newsDAO
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {

        /* TODO: Debugging Logs (Remove Later) */
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        return retrofit
    }

    @Provides
    fun providesNewsApi(retrofit: Retrofit): NewsApi{
        return retrofit.create(NewsApi::class.java)
    }


    @Provides
    fun providesNewsRepo(newsApi: NewsApi, newsDAO: NewsDAO): NewsRepo {
        return NewsRepoImpl(newsApi, newsDAO)
    }


    @Provides
    fun providesGetNewsUseCase(newsRepo: NewsRepo): GetNewsUseCase{
        return GetNewsUseCase(newsRepo)
    }

    @Provides
    fun providesGetSingleHeadlineUseCase(newsDAO: NewsDAO): GetSingleHeadlineUseCase{
        return GetSingleHeadlineUseCase(newsDAO)
    }

}