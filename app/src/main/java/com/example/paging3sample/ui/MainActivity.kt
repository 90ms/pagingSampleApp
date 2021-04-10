package com.example.paging3sample.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.paging.*
import com.example.paging3sample.adapter.PagingAdapter
import com.example.paging3sample.R
import com.example.paging3sample.loading.LoadingAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PagingViewModel>()
    private val adapter =
        PagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupData()
        setPagingListener()
        setupObserver()
    }

    private fun setupData() {
        recycler_view.adapter =
            adapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter { adapter.retry() },
                footer = LoadingAdapter { adapter.retry() }
            )
        viewModel.fetchData()
    }

    private fun setPagingListener() {
        adapter.apply {
            addLoadStateListener {
                Timber.d("addLoadStateListener\nprepend:${it.prepend}\nappend:${it.append}\nrefresh:${it.refresh}")
                if (it.refresh is LoadState.Error) {
                    adapter.retry()
                }
            }
            removeLoadStateListener {
                Timber.d("removeLoadStateListener\nprepend:${it.prepend}\nappend:${it.append}\nrefresh:${it.refresh}")
            }
        }
    }

    private fun setupObserver() {
        viewModel.paging.observe(this@MainActivity, Observer {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        })
    }
}

