package com.legion1900.cleannews.presentation.presenter.impl.dagger

import android.app.Application
import com.legion1900.cleannews.presentation.presenter.impl.dagger.component.AppComponent
import com.legion1900.cleannews.presentation.presenter.impl.dagger.component.DaggerAppComponent
import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.AppModule
import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.NewsRepoModule
import com.legion1900.cleannews.presentation.presenter.impl.dagger.module.NewsServiceModule

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