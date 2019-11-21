package com.legion1900.cleannews.data.base

import com.legion1900.cleannews.data.base.data.Article
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface NewsRepository {
    /*
    * Asks to update cached in 'news' property articles.
    * */
    fun loadNews(topic: String): Observable<List<Article>>

    fun clearCache(): Completable
}