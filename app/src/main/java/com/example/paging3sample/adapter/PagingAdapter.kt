package com.example.paging3sample.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.adapter.viewholder.DividerViewHolder
import com.example.paging3sample.adapter.viewholder.FooterViewHolder
import com.example.paging3sample.adapter.viewholder.HeaderViewHolder
import com.example.paging3sample.adapter.viewholder.ItemViewHolder
import com.example.paging3sample.data.PagingModel
import com.example.paging3sample.data.PagingType

class PagingAdapter : PagingDataAdapter<PagingModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<PagingModel>() {
        override fun areItemsTheSame(oldItem: PagingModel, newItem: PagingModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PagingModel, newItem: PagingModel): Boolean {
            return if (oldItem is PagingModel.Header && newItem is PagingModel.Header) {
                oldItem.value == newItem.value
            } else if (oldItem is PagingModel.Footer && newItem is PagingModel.Footer) {
                oldItem.value == newItem.value
            } else if (oldItem is PagingModel.Item && newItem is PagingModel.Item) {
                oldItem.value == newItem.value
            } else {
                oldItem is PagingModel.Separator && newItem is PagingModel.Separator
            }
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            PagingType.ITEM.ordinal -> ItemViewHolder(parent)
            PagingType.HEADER.ordinal -> HeaderViewHolder(parent)
            PagingType.FOOTER.ordinal -> FooterViewHolder(parent)
            else -> DividerViewHolder(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(getItem(position) as PagingModel.Item)
            is HeaderViewHolder -> holder.bind(getItem(position) as PagingModel.Header)
            is FooterViewHolder -> holder.bind(getItem(position) as PagingModel.Footer)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.type?.ordinal ?: PagingType.ITEM.ordinal
    }
}
