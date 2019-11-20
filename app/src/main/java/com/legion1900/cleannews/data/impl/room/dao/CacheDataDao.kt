package com.legion1900.cleannews.data.impl.room.dao

import androidx.room.*
import com.legion1900.cleannews.data.impl.room.entity.CacheDataEntity
import io.reactivex.Completable
import io.reactivex.Single
import java.util.Date

@Dao
interface CacheDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: CacheDataEntity): Completable

    /*
    * When table is empty returns no rows -> triggers onError.
    * If requested user is present triggers onSuccess.
    * */
    @Query("SELECT date FROM CacheData WHERE topic = :topic LIMIT 1")
    fun getDateFor(topic: String): Single<Date>

    @Query("DELETE FROM CacheData WHERE topic = :topic")
    fun deleteDataFor(topic: String): Completable

    @Query("DELETE FROM CacheData")
    fun clear(): Completable
}