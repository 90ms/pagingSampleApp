package com.example.paging3sample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paging3sample.util.printD
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PagingViewModel>()
    private val adapter = PagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupData()
        setListener()
        setAdapterListener()
        setupObserver()
    }

    private fun setupData() {
        recycler_view.adapter =
            adapter.withLoadStateHeaderAndFooter(DooolLoadStateAdapter { adapter.retry() },
                DooolLoadStateAdapter { adapter.retry() })

        viewModel.init()
    }

    private fun setListener() {
        button.setOnClickListener {
            adapter.refresh()
        }
    }


    private fun setAdapterListener() {
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
//            addDataRefreshListener {
//                "addDataRefreshListener : $it".printD()
//            }
//            removeDataRefreshListener {
//                "removeDataRefreshListener : $it".printD()
//            }
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

