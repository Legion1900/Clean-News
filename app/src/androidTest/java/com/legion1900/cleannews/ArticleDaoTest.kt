package com.legion1900.cleannews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import com.legion1900.cleannews.data.impl.room.entity.CacheDataEntity
import com.legion1900.cleannews.utils.DataProvider
import com.legion1900.cleannews.utils.DataProvider.TOPICS
import com.legion1900.cleannews.utils.DatabaseProvider
import org.assertj.core.api.Assertions
import org.assertj.core.api.MapAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    private lateinit var db: CacheDatabase

    @Before
    fun onPrepareDb() {
        db = DatabaseProvider.provideInMemoryDb(CacheDatabase::class.java)
        cache.forEach { db.cacheDataDao().insert(it).blockingAwait() }
    }

    @After
    fun onCloseDb() {
        db.close()
    }

    @Test
    fun insert_test() {
        val dao = db.articleDao()
        dao.insert(*entities).blockingAwait()

        val real = mutableMapOf<String, List<Article>>()
        for (topic in TOPICS) {
            real += topic to dao.getArticlesFor(topic).blockingFirst()
        }

        Assertions.assertThat(articles).contains(*real.entries.toTypedArray())
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
