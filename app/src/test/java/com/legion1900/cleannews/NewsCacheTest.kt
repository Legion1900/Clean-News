package com.legion1900.cleannews

import com.legion1900.cleannews.data.impl.NewsCache
import com.legion1900.cleannews.data.impl.room.dao.ArticleDao
import com.legion1900.cleannews.data.impl.room.dao.CacheDataDao
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import com.legion1900.cleannews.utils.DataProvider
import com.legion1900.cleannews.utils.DataProvider.TOPICS
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class NewsCacheTest {

    @Mock
    lateinit var db: CacheDatabase

    @Mock
    lateinit var articleDao: ArticleDao

    @Mock
    lateinit var cacheDao: CacheDataDao

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    lateinit var newsCache: NewsCache

    @Before
    fun onPrepareDbMock() {
        Mockito.`when`(db.articleDao()).thenReturn(articleDao)
        Mockito.`when`(db.cacheDataDao()).thenReturn(cacheDao)
        newsCache = NewsCache(db)
    }

    @Test
    fun writeArticles_test() {
        newsCache.writeArticles(topic, date, articles)

        verify(cacheDao).deleteDataFor(topic)
        verify(cacheDao).insert(any())
        verify(articleDao).insert(any())
    }

    @Test
    fun readArticles_test() {
        newsCache.readArticles(topic)

        verify(articleDao).getArticlesFor(topic)
    }

    @Test
    fun lastModified_test() {
        newsCache.lastModified(topic)

        verify(cacheDao).getDateFor(topic)
    }

    @Test
    fun clearCache_test() {
        newsCache.clearCache()

        verify(cacheDao).clear()
    }

    companion object Data {
        val date = Calendar.getInstance().time
        val topic = TOPICS[0]
        val articles = DataProvider.buildArticleList(20)
    }
}
