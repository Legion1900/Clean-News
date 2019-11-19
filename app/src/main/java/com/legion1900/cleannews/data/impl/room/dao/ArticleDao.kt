package com.legion1900.cleannews.data.impl.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.impl.room.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Insert
    fun insert(vararg articles: ArticleEntity)

    @Query("SELECT article FROM Article WHERE topic IN (:topic)")
    fun getArticlesFor(topic: String): List<Article>
}