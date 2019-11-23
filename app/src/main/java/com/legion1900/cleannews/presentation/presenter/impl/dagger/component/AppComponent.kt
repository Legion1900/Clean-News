package com.legion1900.cleannews.presentation.presenter.impl.dagger.component

import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.AppModule
import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.NewsRepoModule
import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.NewsServiceModule
import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.PresenterModule
import com.legion1900.cleannews.presentation.view.NewsfeedActivity
import com.legion1900.cleannews.presentation.view.adapters.NewsAdapter
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        NewsRepoModule::class,
        NewsServiceModule::class,
        PresenterModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity: NewsfeedActivity)
    fun inject(adapter: NewsAdapter)
    // TODO: add inject for article activity
}