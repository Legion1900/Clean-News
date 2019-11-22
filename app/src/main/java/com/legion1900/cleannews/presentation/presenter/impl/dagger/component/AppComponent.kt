package com.legion1900.cleannews.presentation.presenter.impl.dagger.component

import com.legion1900.cleannews.presentation.presenter.impl.NewsPresenter
import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.AppModule
import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.NewsRepoModule
import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.NewsServiceModule
import com.legion1900.cleannews.presentation.view.NewsfeedActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NewsRepoModule::class, NewsServiceModule::class])
@Singleton
interface AppComponent {
    fun inject(presenter: NewsPresenter)
    fun inject(activity: NewsfeedActivity)
    // TODO: add inject for article activity
}