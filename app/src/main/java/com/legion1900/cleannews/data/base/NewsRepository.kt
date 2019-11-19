package com.legion1900.cleannews.data.base

import com.legion1900.cleannews.data.base.data.Article
import io.reactivex.rxjava3.core.Single

interface NewsRepository {
    /*
    * Asks to update cached in 'news' property articles.
    * */
    fun loadNews(topic: String): Single<List<Article>>

    fun clearCache()
}