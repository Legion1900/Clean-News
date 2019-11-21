package com.legion1900.cleannews

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.cleannews.data.impl.NewsCache
import com.legion1900.cleannews.data.impl.NewsRepo
import com.legion1900.cleannews.data.impl.retrofit.NewsService
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import com.legion1900.cleannews.utils.DataProvider
import com.legion1900.cleannews.utils.DataProvider.TOPICS
import com.legion1900.cleannews.utils.DatabaseProvider
import com.legion1900.cleannews.utils.TestUtils.any
import io.reactivex.Observable
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class NewsRepoTest {
    @Mock
    lateinit var service: NewsService

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repo: NewsRepo

    @Before
    fun onInit() {
        val db = DatabaseProvider.provideInMemoryDb(CacheDatabase::class.java)
        prepareRetrofit()
        repo = NewsRepo(NewsCache(db), service)
    }

    private fun prepareRetrofit() {
        `when`(service.queryNews(any())).thenReturn(Observable.just(response))
    }

    @Test
    fun loadNews_emptyDb_test() {
        repo.loadNews(topic).doOnNext { list ->
            Assertions.assertThat(list.all {
                it.title.toString().contains(NewsRepo.MARK)
            }).isTrue()
        }.test().awaitTerminalEvent()
    }

    private companion object {
        const val DEF_NUM = 20
        val topic = TOPICS[Random.nextInt(TOPICS.size)]
        val response = DataProvider.buildResponse(DataProvider.buildArticleList(DEF_NUM))
    }
}