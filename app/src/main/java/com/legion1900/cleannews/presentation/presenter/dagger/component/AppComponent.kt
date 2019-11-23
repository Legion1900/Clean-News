package com.legion1900.cleannews.presentation.presenter.dagger.component

import com.legion1900.cleannews.presentation.presenter.dagger.module.AppModule
import com.legion1900.cleannews.presentation.presenter.dagger.module.NewsRepoModule
import com.legion1900.cleannews.presentation.presenter.dagger.module.NewsServiceModule
import com.legion1900.cleannews.presentation.presenter.dagger.module.PresenterModule
import com.legion1900.cleannews.presentation.view.activity.NewsfeedActivity
import com.legion1900.cleannews.presentation.view.activity.adapters.NewsAdapter
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
}