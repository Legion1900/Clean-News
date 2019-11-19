package com.legion1900.cleannews.data.impl.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.legion1900.cleannews.data.impl.room.Converter
import com.legion1900.cleannews.data.impl.room.dao.ArticleDao
import com.legion1900.cleannews.data.impl.room.dao.CacheDataDao
import com.legion1900.cleannews.data.impl.room.entity.ArticleEntity
import com.legion1900.cleannews.data.impl.room.entity.CacheDataEntity

@Database(
    entities = [ArticleEntity::class, CacheDataEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    abstract fun cacheDataDao(): CacheDataDao

    companion object {
        const val DB_NAME = "NewsCache"
    }
}