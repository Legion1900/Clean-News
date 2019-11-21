package com.legion1900.cleannews.utils

import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.base.data.Response
import com.legion1900.cleannews.data.impl.room.entity.ArticleEntity
import com.legion1900.cleannews.data.impl.room.entity.CacheDataEntity
import com.legion1900.cleannews.data.impl.utils.EntityConverter.articlesToEntities
import java.util.*
import kotlin.random.Random

object DataProvider {
    val TOPICS = arrayOf("Software", "Cybersecurity", "Cinema", "Sport")

    private const val STUB = "Stub"
    private const val AUTHOR = "Author"
    private const val TITLE = "Title"
    private const val PUB_AT = "01-01-2020"
    private const val SOURCE = "Source"
    private const val URL = "https://example.com"
    private const val DESC = "Foo Bar"

    fun buildDefaultCacheEntities(): List<CacheDataEntity> {
        val entities = mutableListOf<CacheDataEntity>()
        for (topic in TOPICS)
            entities += CacheDataEntity(topic,
                getRandomDate()
            )
        return entities
    }

    private fun getRandomDate() = Date(Random.nextLong())

    fun buildDefaultArticleEntities(num: Int): List<ArticleEntity> {
        val entities = mutableListOf<ArticleEntity>()
        for (topic in TOPICS) {
            val articles =
                buildArticleList(num)
            entities += articlesToEntities(articles, topic)
        }
        return entities
    }

    private fun buildArticle() = Article(
        AUTHOR,
        TITLE,
        PUB_AT,
        SOURCE,
        URL,
        DESC
    )

    fun buildArticleList(num: Int): List<Article> {
        val list = mutableListOf<Article>()
        for (i in 0 until num)
            list += buildArticle()
        return list
    }

    fun buildResponse(articles: List<Article>) = Response(STUB, STUB, STUB, articles)
}