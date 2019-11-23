package com.legion1900.cleannews.presentation.presenter.impl.dagger.module

import android.content.Context
import androidx.room.Room
import com.legion1900.cleannews.data.base.CacheRepository
import com.legion1900.cleannews.data.base.NewsRepository
import com.legion1900.cleannews.data.impl.CacheRepo
import com.legion1900.cleannews.data.impl.NewsRepo
import com.legion1900.cleannews.data.impl.retrofit.NewsService
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsRepoModule {
    @Provides
    @Singleton
    fun provideCacheDatabase(context: Context): CacheDatabase =
        Room.databaseBuilder(context, CacheDatabase::class.java, CacheDatabase.DB_NAME).build()

    @Provides
    @Singleton
    fun provideCacheRepo(db: CacheDatabase): CacheRepository = CacheRepo(db)

    @Provides
    @Singleton
    fun provideNewsRepo(cache: CacheRepository, service: NewsService): NewsRepository =
        NewsRepo(cache, service)
}