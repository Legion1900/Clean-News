package com.legion1900.cleannews.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.legion1900.cleannews.data.base.NewsRepository
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.presentation.presenter.impl.NewsPresenter
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import utils.DataProvider
import utils.DataProvider.TOPICS
import java.lang.Exception
import kotlin.random.Random


@RunWith(MockitoJUnitRunner::class)
class NewsPresenterTest {


    private val mockRepo: NewsRepository = mock()

    private val presenter = NewsPresenter(mockRepo)

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @After
    fun onMockReset() {
        reset(mockRepo)
    }

    @Test
    fun updateNewsfeed_test() {
        `when`(mockRepo.loadNews(anyString())).thenReturn(normalSource)
        presenter.updateNewsfeed(topic)
        val actual = presenter.articles.value
        assertThat(actual).isEqualTo(articles)
    }

    @Test
    fun updateNewsfeed_exceptionHandling_test() {
        `when`(mockRepo.loadNews(anyString())).thenReturn(errorSource)
        presenter.updateNewsfeed(topic)
        assertThat(presenter.isError.value).isTrue()
    }

    private companion object {
        val topic = TOPICS[Random.nextInt(TOPICS.size)]
        val articles = DataProvider.buildArticleList(20)
        val normalSource = Observable.just(articles)
        val errorSource = Observable.create<List<Article>> { throw Exception() }
    }
}