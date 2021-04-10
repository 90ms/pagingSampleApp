package com.example.paging3sample.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.R
import com.example.paging3sample.data.PagingModel
import com.example.paging3sample.databinding.ItemHeaderBinding

class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
) {
    private val binding = ItemHeaderBinding.bind(itemView)

    fun bind(item: PagingModel.Header) {
        binding.item = item
        binding.executePendingBindings()
    }
}