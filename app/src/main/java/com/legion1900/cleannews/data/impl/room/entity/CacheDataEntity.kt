package com.legion1900.cleannews.data.impl.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "CacheData")
data class CacheDataEntity(
    @PrimaryKey val topic: String,
    val date: Date
)