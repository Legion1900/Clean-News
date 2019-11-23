package com.legion1900.cleannews.presentation.presenter.dagger.component

import com.legion1900.cleannews.presentation.presenter.dagger.module.ImageLoaderModule
import com.legion1900.cleannews.presentation.view.activity.ArticleActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ImageLoaderModule::class
    ]
)
@Singleton
interface ArticleViewComponent {
    fun inject(activity: ArticleActivity)
}