package com.legion1900.cleannews.presentation.presenter.dagger

import android.app.Application
import com.legion1900.cleannews.presentation.presenter.dagger.component.AppComponent
import com.legion1900.cleannews.presentation.presenter.dagger.component.DaggerAppComponent
import com.legion1900.cleannews.presentation.presenter.dagger.module.AppModule
import com.legion1900.cleannews.presentation.presenter.dagger.module.NewsRepoModule
import com.legion1900.cleannews.presentation.presenter.dagger.module.NewsServiceModule

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mAppComponent = buildAppComponent()
    }

    private fun buildAppComponent() = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .newsServiceModule(NewsServiceModule())
        .newsRepoModule(NewsRepoModule())
        .build()

    companion object {
        val appComponent
            get() = mAppComponent
        private lateinit var mAppComponent: AppComponent
    }
}