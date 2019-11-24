package com.legion1900.cleannews.repo

import com.legion1900.cleannews.data.impl.CacheRepo
import com.legion1900.cleannews.data.impl.room.dao.ArticleDao
import com.legion1900.cleannews.data.impl.room.dao.CacheDataDao
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import com.legion1900.cleannews.data.impl.utils.TimeUtils
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import utils.DataProvider
import utils.DataProvider.TOPICS
import utils.TestUtils.any
import kotlin.random.Random

@RunWith(MockitoJUnitRunner::class)
class CacheRepoTest {
    @Mock
    lateinit var db: CacheDatabase
    @Mock
    lateinit var articleDao: ArticleDao
    @Mock
    lateinit var cacheDao: CacheDataDao

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    private lateinit var cacheRepo: CacheRepo

    @Before
    fun onMockInit() {
        `when`(db.cacheDataDao()).thenReturn(cacheDao)
        `when`(db.articleDao()).thenReturn(articleDao)
        initArticleDao()
        initCacheDao()

        cacheRepo = CacheRepo(db)
    }

    private fun initArticleDao() {
        `when`(articleDao.insert(any())).thenReturn(
            Completable.create { emitter: CompletableEmitter -> emitter.onComplete() }
        )
        `when`(articleDao.getArticlesFor(topic)).thenReturn(Observable.create { emitter ->
            emitter.onNext(articles)
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
        cacheRepo.writeArticles(
            topic,
            date,
            articles
        )
            .test()
            .assertComplete()
            .awaitTerminalEvent()

        verify(cacheDao).deleteDataFor(any())
        verify(cacheDao).insert(any())
        verify(articleDao).insert(any())
    }

    @Test
    fun readArticles_test() {
        cacheRepo.readArticles(topic).blockingFirst()
        verify(articleDao).getArticlesFor(topic)
    }

    @Test
    fun lastModified_tes() {
        cacheRepo.lastModified(topic).blockingGet()
        verify(cacheDao).getDateFor(topic)
    }

    @Test
    fun clearCache_test() {
        cacheRepo.clearCache().blockingAwait()
        verify(cacheDao).clear()
    }

    private companion object {
        const val LIST_SIZE = 20

        val topic = TOPICS[Random.nextInt(TOPICS.size)]
        val date = TimeUtils.getCurrentDate()
        val articles = DataProvider.buildArticleList(LIST_SIZE)
    }
}