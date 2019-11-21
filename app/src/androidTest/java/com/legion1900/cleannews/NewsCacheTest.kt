package com.legion1900.cleannews

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.cleannews.data.impl.NewsCache
import com.legion1900.cleannews.data.impl.room.dao.ArticleDao
import com.legion1900.cleannews.data.impl.room.dao.CacheDataDao
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import com.legion1900.cleannews.data.impl.utils.TimeUtils
import com.legion1900.cleannews.utils.DataProvider
import com.legion1900.cleannews.utils.DataProvider.TOPICS
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
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

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var newsCache: NewsCache

    @Before
    fun onMockInit() {
        `when`(db.cacheDataDao()).thenReturn(cacheDao)
        `when`(db.articleDao()).thenReturn(articleDao)
        initArticleDao()
        initCacheDao()

        newsCache = NewsCache(db)
    }

    private fun initArticleDao() {
        `when`(articleDao.insert(any())).thenReturn(
            Completable.create { emitter: CompletableEmitter -> emitter.onComplete() }
        )
        `when`(articleDao.getArticlesFor(topic)).thenReturn(Observable.create { emitter ->
            emitter.onNext(
                articles
            )
        })
    }

    private fun initCacheDao() {
        `when`(cacheDao.clear()).thenReturn(
            Completable.create { emitter: CompletableEmitter -> emitter.onComplete() }
        )
        `when`(cacheDao.insert(any())).thenReturn(
            Completable.create { emitter: CompletableEmitter -> emitter.onComplete() }
        )
        `when`(cacheDao.deleteDataFor(topic)).thenReturn(
            Completable.create { emitter: CompletableEmitter -> emitter.onComplete() }
        )
        `when`(cacheDao.getDateFor(topic)).thenReturn(Single.create { emitter ->
            emitter.onSuccess(
                date
            )
        })
    }

    @Test
    fun writeArticles_test() {
        newsCache.writeArticles(topic, date, articles)
            .test()
            .assertComplete()
            .awaitTerminalEvent()

        verify(cacheDao).deleteDataFor(any())
        verify(cacheDao).insert(any())
        verify(articleDao).insert(any())
    }

    private fun <T> any() = Mockito.any<T>() as T

    private companion object {
        const val LIST_SIZE = 20

        val topic = TOPICS[Random.nextInt(TOPICS.size)]
        val date = TimeUtils.getCurrentDate()
        val articles = DataProvider.buildArticleList(LIST_SIZE)
    }
}