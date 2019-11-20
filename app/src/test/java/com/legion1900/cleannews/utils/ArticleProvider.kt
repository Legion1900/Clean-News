package com.legion1900.cleannews.utils

import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.impl.room.entity.ArticleEntity
import com.legion1900.cleannews.data.impl.room.entity.CacheDataEntity
import com.legion1900.cleannews.data.impl.utils.EntityConverter.articlesToEntities
import java.util.*
import kotlin.random.Random

object ArticleProvider {
    val TOPICS = arrayOf("Software", "Cybersecurity", "Cinema", "Sport")

    private const val AUTHOR = "Author"
    private const val TITLE = "Title"
    private const val PUB_AT = "01-01-2020"
    private const val SOURCE = "Source"
    private const val URL = "https://example.com"
    private const val DESC = "Foo Bar"

    private fun buildArticle() = Article(AUTHOR, TITLE, PUB_AT, SOURCE, URL, DESC)

    fun buildArticleList(num: Int): List<Article> {
        val list = mutableListOf<Article>()
        for (i in 0 until num)
            list += buildArticle()
        return list
    }
}