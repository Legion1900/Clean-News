package com.legion1900.cleannews.presentation.presenter.base

import androidx.lifecycle.LiveData
import com.legion1900.cleannews.data.base.data.Article

interface Presenter {
    val articles: LiveData<List<Article>>
    val isLoading: LiveData<Boolean>
    val isError: LiveData<Boolean>
    fun updateNewsfeed(topic: String)
}