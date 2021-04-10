package com.example.paging3sample.loading

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class LoadingAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadingViewHolder(parent, retry)

    override fun onBindViewHolder(holder: LoadingViewHolder, loadState: LoadState) =
        holder.bind(loadState)
}