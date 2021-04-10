package com.example.paging3sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3sample.databinding.ItemFooterBinding
import com.example.paging3sample.databinding.ItemHeaderBinding
import com.example.paging3sample.databinding.ItemItemBinding

class PagingAdapter : PagingDataAdapter<PagingModel, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PagingModel>() {
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

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        PagingType.ITEM.ordinal -> PagingItemViewHolder(parent)
        PagingType.HEADER.ordinal -> PagingHeaderViewHolder(parent)
        PagingType.FOOTER.ordinal -> PagingFooterViewHolder(parent)
        else -> PagingSeparatorViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PagingItemViewHolder -> holder.bind(getItem(position) as PagingModel.Item)
            is PagingHeaderViewHolder -> holder.bind(getItem(position) as PagingModel.Header)
            is PagingFooterViewHolder -> holder.bind(getItem(position) as PagingModel.Footer)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.type?.ordinal ?: PagingType.ITEM.ordinal
    }

    class PagingItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
    ) {
        private val dataBinding = ItemItemBinding.bind(itemView)

        fun bind(paging: PagingModel.Item) {
            dataBinding.item = paging
            dataBinding.executePendingBindings()
        }
    }

    class PagingHeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
    ) {
        private val dataBinding = ItemHeaderBinding.bind(itemView)

        fun bind(paging: PagingModel.Header) {
            dataBinding.item = paging
            dataBinding.executePendingBindings()
        }
    }

    class PagingFooterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
    ) {
        private val dataBinding = ItemFooterBinding.bind(itemView)

        fun bind(paging: PagingModel.Footer) {
            dataBinding.item = paging
            dataBinding.executePendingBindings()
        }
    }

    class PagingSeparatorViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_seperate, parent, false)
    )
}
