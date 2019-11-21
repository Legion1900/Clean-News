package com.legion1900.cleannews.utils

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.platform.app.InstrumentationRegistry

object DatabaseProvider {
    private val context = InstrumentationRegistry.getInstrumentation().context

    fun <T : RoomDatabase> provideInMemoryDb(dbClass: Class<T>): T = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
        dbClass
    )
        .allowMainThreadQueries()
        .build()
}