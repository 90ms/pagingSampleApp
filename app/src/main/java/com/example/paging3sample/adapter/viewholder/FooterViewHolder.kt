package com.example.paging3sample.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.R
import com.example.paging3sample.data.PagingModel
import com.example.paging3sample.databinding.ItemFooterBinding

class FooterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
) {
    private val binding = ItemFooterBinding.bind(itemView)

    fun bind(item: PagingModel.Footer) {
        binding.item = item
        binding.executePendingBindings()
    }
}