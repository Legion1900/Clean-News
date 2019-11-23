package com.legion1900.cleannews.presentation.presenter.dagger.module

import com.bumptech.glide.Glide
import com.legion1900.cleannews.presentation.view.activity.ArticleActivity
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageLoaderModule(val activity: ArticleActivity) {
    @Provides
    @Singleton
    fun providePicasso(): Picasso = Picasso.get()

    @Provides
    fun provideRequestManager() = Glide.with(activity)
}