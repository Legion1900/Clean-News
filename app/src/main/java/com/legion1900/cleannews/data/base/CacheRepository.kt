package com.legion1900.cleannews.data.base

import com.legion1900.cleannews.data.base.data.Article
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

interface CacheRepository {
    fun writeArticles(topic: String, date: Date, articles: List<Article>): Completable

    fun readArticles(topic: String): Observable<List<Article>>

    /*
    * Date when specified topic was updated last time.
    * */
    fun lastModified(topic: String): Single<Date>

    fun clearCache(): Completable
}