package com.legion1900.cleannews.data.impl.utils

import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.impl.room.entity.ArticleEntity

object EntityConverter {
    fun articlesToEntities(articles: List<Article>, topic: String? = null): List<ArticleEntity> {
        val entities = mutableListOf<ArticleEntity>()
        for (a in articles)
            entities += ArticleEntity(a, topic)
        return entities
    }
}