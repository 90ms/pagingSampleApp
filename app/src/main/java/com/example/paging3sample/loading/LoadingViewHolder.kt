package com.example.paging3sample.loading

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.R
import com.example.paging3sample.databinding.ItemLoadingBinding

class LoadingViewHolder(parent: ViewGroup, private val retry: () -> Unit) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
    ) {

    private val binding = ItemLoadingBinding.bind(itemView)

    fun bind(loadState: LoadState) {
        binding.run {
            retryButton.setOnClickListener { retry() }
            isLoading = loadState is LoadState.Loading
            isError = loadState is LoadState.Error
            errorMessage = (loadState as? LoadState.Error)?.error?.message ?: ""
            executePendingBindings()
        }
    }
}