package com.legion1900.cleannews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import com.legion1900.cleannews.data.impl.room.entity.CacheDataEntity
import utils.DataProvider
import utils.DataProvider.TOPICS
import com.legion1900.cleannews.utils.DatabaseProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    private lateinit var db: CacheDatabase

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onPrepareDb() {
        db = DatabaseProvider.provideInMemoryDb(CacheDatabase::class.java)
        cache.forEach { db.cacheDataDao().insert(it).test().awaitTerminalEvent() }
    }

    @After
    fun onCloseDb() {
        db.close()
    }

    @Test
    fun insert_test() {
        val dao = db.articleDao()
        dao.insert(*entities).test()
            .assertComplete()
            .awaitTerminalEvent()

        for (topic in TOPICS) {
            dao.getArticlesFor(topic).test()
                .assertValue { it == articles[topic] }
        }
    }

    private companion object {
        val entities = DataProvider.buildDefaultArticleEntities(20).toTypedArray()
        val articles: Map<String, List<Article>>
        val cache: List<CacheDataEntity> = DataProvider.buildDefaultCacheEntities()

        init {
            val groups = entities.groupBy { it.topic }
            articles = groups.mapValues { pair ->
                pair.value.map { it.article }
            }
        }
    }
}
