package com.legion1900.cleannews.presentation.view.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.RequestManager
import com.legion1900.cleannews.R
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.databinding.ActivityArticleBinding
import com.legion1900.cleannews.presentation.presenter.dagger.component.DaggerArticleViewComponent
import com.legion1900.cleannews.presentation.presenter.dagger.module.ImageLoaderModule
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ArticleActivity : AppCompatActivity() {

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var glide: RequestManager

    private lateinit var binding: ActivityArticleBinding

    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article)

        initInjection()
        receiveArticle()
        initImageViews()
        initTextViews()
    }

    private fun initInjection() {
        DaggerArticleViewComponent
            .builder()
            .imageLoaderModule(ImageLoaderModule(this))
            .build()
            .inject(this)
    }

    private fun receiveArticle() {
        article = intent.getParcelableExtra(KEY_ARTICLE)!!
    }

    private fun initImageViews() {
        article.urlToImage?.let {
            picasso.load(it)
                .placeholder(R.drawable.ic_image_grey_24dp)
                .error(R.drawable.ic_broken_image_grey_24dp)
                .into(binding.ivPicasso)
            glide.load(it)
                .placeholder(R.drawable.ic_image_grey_24dp)
                .error(R.drawable.ic_broken_image_grey_24dp)
                .into(binding.ivGlide)
        } ?: run {
            val group = findViewById<LinearLayout>(R.id.group_pictures)
            group.visibility = View.GONE
        }
    }

    private fun initTextViews() {
        binding.run {
            tvTitle.text = getString(R.string.title, article.title)
            tvSourceName.text = getString(R.string.source, article.sourceName)
            tvDescription.text = article.description
        }
    }

    companion object {
        const val KEY_ARTICLE = "article"
    }
}
