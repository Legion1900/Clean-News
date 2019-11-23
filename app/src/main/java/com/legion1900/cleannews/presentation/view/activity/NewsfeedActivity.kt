package com.legion1900.cleannews.presentation.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.legion1900.cleannews.BuildConfig
import com.legion1900.cleannews.R
import com.legion1900.cleannews.data.base.data.Article
import com.legion1900.cleannews.databinding.ActivityNewsfeedBinding
import com.legion1900.cleannews.presentation.presenter.base.Presenter
import com.legion1900.cleannews.presentation.presenter.dagger.NewsApp
import com.legion1900.cleannews.presentation.view.activity.adapters.NewsAdapter
import com.legion1900.cleannews.presentation.view.activity.listener.OnTopicSelectedListener
import com.legion1900.cleannews.presentation.view.dialogs.ErrorDialog
import javax.inject.Inject

class NewsfeedActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: Presenter

    private lateinit var binding: ActivityNewsfeedBinding

    private val dialog = ErrorDialog.createErrorDialog(
        R.string.msg_err,
        R.string.btn_positive,
        object : ErrorDialog.PositiveCallback {
            override fun onPositive() {
                requestNews()
            }
        }
    )

    private val adapter = NewsAdapter(View.OnClickListener {
        val intent = Intent(this, ArticleActivity::class.java)
        val i = binding.rvNews.getChildAdapterPosition(it)
        val article = presenter.articles.value?.get(i)
        intent.putExtra(ArticleActivity.KEY_ARTICLE, article)
        startActivityTransition(intent, it)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initSwipeRefresh()
        initRecyclerView()
        initSpinner()
        initDialog()

        requestNews()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_newsfeed
        )
        NewsApp.appComponent.inject(this)
        binding.presenter = presenter
    }

    private fun initSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(this::requestNews)
    }

    private fun initRecyclerView() {
        binding.rvNews.adapter = adapter
    }

    private fun initSpinner() {
        binding.run {
            spinnerTopics.setSelection(0, false)
            spinnerTopics.onItemSelectedListener =
                OnTopicSelectedListener(this@NewsfeedActivity::requestNews)
        }
    }

    private fun initDialog() {
        presenter.isError.observe(
            this,
            Observer<Boolean> {
                if (it) dialog.show(
                    supportFragmentManager,
                    BuildConfig.APPLICATION_ID
                ) else if (dialog.isDetached)
                    dialog.dismiss()
            })
        presenter.isLoading.observe(
            this,
            Observer<Boolean> {
                binding.swipeRefresh.isRefreshing = it
            }
        )
        presenter.articles.observe(
            this,
            Observer<List<Article>> {
                adapter.updateData(it)
            }
        )
    }

    private fun requestNews() {
        presenter.updateNewsfeed(binding.spinnerTopics.selectedItem as String)
    }

    private fun startActivityTransition(intent: Intent, fromView: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                fromView,
                fromView.transitionName
            ).toBundle()
            startActivity(intent, options)
        } else
            startActivity(intent)
    }
}
