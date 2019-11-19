package com.legion1900.cleannews.data.impl.room.dao

import androidx.room.*
import com.legion1900.cleannews.data.impl.room.entity.CacheDataEntity
import java.util.*

@Dao
interface CacheDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: CacheDataEntity)

    @Query("SELECT date FROM CacheData WHERE topic = :topic LIMIT 1")
    fun getDateFor(topic: String): Date?

    @Query("DELETE FROM CacheData WHERE topic = :topic")
    fun deleteDataFor(topic: String)

    @Query("DELETE FROM CacheData")
    fun clear()
}