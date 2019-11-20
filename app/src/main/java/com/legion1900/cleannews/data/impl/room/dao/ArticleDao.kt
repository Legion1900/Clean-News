package com.legion1900.cleannews.data.impl.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.data.impl.room.entity.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ArticleDao {
    @Insert
    fun insert(vararg articles: ArticleEntity): Completable

    @Query("SELECT article FROM Article WHERE topic IN (:topic)")
    fun getArticlesFor(topic: String): Observable<List<Article>>
}