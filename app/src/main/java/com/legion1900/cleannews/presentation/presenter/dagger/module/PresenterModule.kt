package com.legion1900.cleannews.presentation.presenter.dagger.module

import com.legion1900.cleannews.data.impl.NewsRepo
import com.legion1900.cleannews.presentation.presenter.base.Presenter
import com.legion1900.cleannews.presentation.presenter.impl.NewsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun provideNewsPresenter(repo: NewsRepo): Presenter  = NewsPresenter(repo)
}