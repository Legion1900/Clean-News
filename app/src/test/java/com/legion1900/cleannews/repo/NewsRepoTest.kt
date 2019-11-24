package com.legion1900.cleannews.repo

import com.legion1900.cleannews.data.base.CacheRepository
import com.legion1900.cleannews.data.impl.NewsRepo
import com.legion1900.cleannews.data.impl.retrofit.NewsService
import com.legion1900.cleannews.data.impl.utils.TimeUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import utils.DataProvider
import utils.DataProvider.TOPICS
import utils.TestUtils.any
import java.util.*
import kotlin.random.Random

@RunWith(MockitoJUnitRunner::class)
class NewsRepoTest {
    @Mock
    lateinit var service: NewsService

    @Mock
    lateinit var newsCache: CacheRepository

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    private lateinit var repo: NewsRepo

    @Before
    fun onInit() {
        prepareRetrofit()
        prepareCacheRepo()
        repo = NewsRepo(newsCache, service)
    }

    @After
    fun onResetMocks() = reset(service, newsCache)

    private fun prepareRetrofit() {
        `when`(service.queryNews(any())).thenReturn(Observable.just(response))
    }

    private fun prepareCacheRepo() {
        `when`(newsCache.clearCache()).thenReturn(Completable.create { it.onComplete() })
        `when`(newsCache.readArticles(topic)).thenReturn(Observable.just(
            articles
        ))
        `when`(newsCache.lastModified(any())).thenReturn(Single.just(DATE_OLD_CACHE))
        `when`(
            newsCache.writeArticles(anyString(), any(), anyList())
        ).thenReturn(Completable.create { it.onComplete() })
    }

    @Test
    fun loadNews_withNoCache_test() {
        repo.loadNews(topic).doOnNext { list ->
            assertAllElements(list) { it.title.toString().length < NewsRepo.MAX_LENGTH }
            assertAllElements(list) { it.title.toString().contains(NewsRepo.MARK) }
        }.test().awaitTerminalEvent()

        verify(service).queryNews(anyMap())
        verify(newsCache).writeArticles(anyString(), any(), anyList())
    }

    @Test
    fun loadNews_withExistingCache_test() {
        `when`(newsCache.lastModified(topic)).thenReturn(Single.just(
            DATE_NOW
        ))
        repo.loadNews(topic).test().awaitTerminalEvent()

        verify(service, never()).queryNews(anyMap())
        verify(newsCache, never()).writeArticles(anyString(), any(), anyList())

        verify(newsCache).readArticles(anyString())
    }

    private fun <T> assertAllElements(list: List<T>, predicate: (el: T) -> Boolean) {
        assertThat(list.all { predicate(it) }).isTrue()
    }

    private companion object {
        const val DEF_NUM = 20
        val DATE_OLD_CACHE = Date(0)
        val DATE_NOW = TimeUtils.getCurrentDate()
        val topic = TOPICS[Random.nextInt(TOPICS.size)]
        val articles = DataProvider.buildArticleList(DEF_NUM)
        val response = DataProvider.buildResponse(articles)
    }
}