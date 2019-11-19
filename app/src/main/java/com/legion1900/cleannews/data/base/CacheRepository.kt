package com.legion1900.cleannews.data.base

import com.legion1900.cleannews.data.base.data.Article
import java.util.*

interface CacheRepository {
    fun writeArticles(topic: String, date: Date, articles: List<Article>)

    fun readArticles(topic: String): List<Article>

    /*
    * Date when specified topic was updated last time.
    * */
    fun lastModified(topic: String): Date

    fun clearCache()
}