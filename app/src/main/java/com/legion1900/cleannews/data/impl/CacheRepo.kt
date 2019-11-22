package com.legion1900.cleannews.data.impl

import com.legion1900.cleannews.data.base.CacheRepository
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.impl.room.database.CacheDatabase
import com.legion1900.cleannews.data.impl.room.entity.CacheDataEntity
import com.legion1900.cleannews.data.impl.utils.EntityConverter
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

/*
* Performs operations synchronously.
* */
class CacheRepo(db: CacheDatabase) :
    CacheRepository {

    private val articleDao = db.articleDao()
    private val cacheDao = db.cacheDataDao()

    override fun writeArticles(topic: String, date: Date, articles: List<Article>): Completable {
        val updateCache = updateCacheData(topic, date)
        val entities = EntityConverter.articlesToEntities(articles, topic)
        val articleInsertion = articleDao.insert(*entities.toTypedArray())
        return updateCache.andThen(articleInsertion)
    }

    private fun updateCacheData(topic: String, date: Date): Completable {
        val deletion = cacheDao.deleteDataFor(topic)
        val cache = CacheDataEntity(topic, date)
        val insertion = cacheDao.insert(cache)
        return deletion.andThen(insertion)
    }

    override fun readArticles(topic: String): Observable<List<Article>> =
        articleDao.getArticlesFor(topic)

    override fun lastModified(topic: String): Single<Date> = cacheDao.getDateFor(topic)

    override fun clearCache(): Completable = cacheDao.clear()
}