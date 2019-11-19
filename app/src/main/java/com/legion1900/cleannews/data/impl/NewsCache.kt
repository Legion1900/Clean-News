package com.legion1900.cleannews.data.impl

import com.legion1900.cleannews.data.base.CacheRepository
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import com.legion1900.cleannews.data.impl.room.entity.CacheDataEntity
import com.legion1900.cleannews.data.impl.utils.EntityConverter
import java.util.*

/*
* Performs operations synchronously.
* */
class NewsCache(db: CacheDatabase) :
    CacheRepository {

    private val articleDao = db.articleDao()
    private val cacheDao = db.cacheDataDao()

    override fun writeArticles(topic: String, date: Date, articles: List<Article>) {
        updateCacheData(topic, date)
        val entities = EntityConverter.articlesToEntities(articles, topic)
        articleDao.insert(*entities.toTypedArray())
    }

    private fun updateCacheData(topic: String, date: Date) {
        cacheDao.deleteDataFor(topic)
        val cache = CacheDataEntity(topic, date)
        cacheDao.insert(cache)
    }

    override fun readArticles(topic: String): List<Article> = articleDao.getArticlesFor(topic)

    override fun lastModified(topic: String): Date = cacheDao.getDateFor(topic) ?: DEF_DATE

    override fun clearCache() = cacheDao.clear()

    companion object {
        val DEF_DATE = Date(0)
    }
}