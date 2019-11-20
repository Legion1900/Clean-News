package com.legion1900.cleannews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import com.legion1900.cleannews.utils.DataProvider
import com.legion1900.cleannews.utils.DatabaseProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class CacheDataDaoTest {

    private lateinit var db: CacheDatabase

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun onPrepareDb() {
        db = DatabaseProvider.provideInMemoryDb(CacheDatabase::class.java)
    }

    @After
    fun onCloseDb() {
        db.close()
    }

    @Test
    fun insert_test() {
        populateTable()

        data.forEach {
            db.cacheDataDao().getDateFor(it.topic)
                .test()
                .assertComplete()
                .assertValue(it.date)
                .awaitTerminalEvent()
        }
    }

    @Test
    fun getDateFor_noRow_test() {
        val dao = db.cacheDataDao()

        data.forEach {
            dao.getDateFor(it.topic)
                .test()
                .assertError(EmptyResultSetException::class.java)
                .awaitTerminalEvent()
        }
    }

    @Test
    fun deleteDataFor_test() {
        val dao = db.cacheDataDao()
        populateTable()

        val topic = data[Random.nextInt(data.size)].topic
        dao.deleteDataFor(topic)
            .test()
            .assertComplete()
            .awaitTerminalEvent()

        dao.getDateFor(topic)
            .test()
            .assertError(EmptyResultSetException::class.java)
            .awaitTerminalEvent()
    }

    private fun populateTable() {
        val dao = db.cacheDataDao()
        data.forEach { dao.insert(it).blockingAwait() }
    }

    private companion object {
        val data = DataProvider.buildDefaultCacheEntities()
    }
}