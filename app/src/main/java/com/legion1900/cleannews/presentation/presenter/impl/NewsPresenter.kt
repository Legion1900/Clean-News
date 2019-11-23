package com.legion1900.cleannews.presentation.presenter.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.legion1900.cleannews.data.base.NewsRepository
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.presentation.presenter.base.Presenter
import io.reactivex.disposables.CompositeDisposable

class NewsPresenter(private val repo: NewsRepository) : ViewModel(), Presenter {

    override val articles: LiveData<List<Article>>
        get() = mArticles
    override val isLoading: LiveData<Boolean>
        get() = mIsLoading
    override val isError: LiveData<Boolean>
        get() = mIsError

    private val mArticles = MutableLiveData<List<Article>>()
    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsError = MutableLiveData<Boolean>()

    private val disposables = CompositeDisposable()

    override fun updateNewsfeed(topic: String) {
        mIsError.value = false
        mIsLoading.value = true
        disposables.clear()
        setupLoading(topic)
    }

    private fun setupLoading(topic: String) {
        disposables.add(
            repo.loadNews(topic).doFinally { disposables.clear() }.subscribe(
                {
                    mIsLoading.postValue(false)
                    mArticles.postValue(it)
                },
                {
                    mIsLoading.postValue(false)
                }
            )
        )
    }
}