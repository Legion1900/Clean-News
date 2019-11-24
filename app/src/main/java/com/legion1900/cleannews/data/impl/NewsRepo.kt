package com.legion1900.cleannews.data.impl

import com.legion1900.cleannews.data.base.CacheRepository
import com.legion1900.cleannews.data.base.NewsRepository
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.impl.retrofit.NewsService
import com.legion1900.cleannews.data.impl.utils.TimeUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/*
* NewsRepo should be injected.
* */
class NewsRepo @Inject constructor(
    private val cache: CacheRepository,
    private val service: NewsService
) : NewsRepository {

    private val disposables = CompositeDisposable()

    override fun loadNews(topic: String): Observable<List<Article>> {
        val date = TimeUtils.getCurrentDate()
        return isOutdated(topic, date).flatMapObservable { isOutdated ->
            val source = if (isOutdated) {
                val data = loadNFilter(topic, date)
                cacheArticles(topic, date, data)
            } else
                cache.readArticles(topic)
            source.observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun isOutdated(topic: String, date: Date): Single<Boolean> {
        return cache.lastModified(topic).onErrorReturnItem(DEF_DATE).map {
            TimeUtils.subtract(date, it) >= TIMEOUT
        }
    }

    private fun loadNFilter(topic: String, date: Date): Observable<List<Article>> {
        val args = NewsService.buildQuery(topic, date)
        return service.queryNews(args)
            .subscribeOn(Schedulers.io())
            .flatMap { Observable.fromIterable(it.articles) }
            .filter { it.title.toString().length < MAX_LENGTH }
            .doOnNext { it.title += MARK }
            .buffer(NewsService.DEF_PAGE_SIZE)
            .cache()
    }

    private fun cacheArticles(topic: String, date: Date, news: Observable<List<Article>>) =
        news.doOnNext {
            cache.writeArticles(
                topic,
                date,
                it
            )
        }

    override fun clearCache(): Completable {
        disposables.clear()
        return cache.clearCache()
    }

    companion object {
        const val TIMEOUT = 60_000
        const val MARK = "Filtered"
        const val MAX_LENGTH = 50
        val DEF_DATE = Date(0)
    }
}