package com.example.paging3sample.data

sealed class PagingModel(val type: PagingType) {
    data class Item(val value: String) : PagingModel(PagingType.ITEM)
    data class Header(val value: String) : PagingModel(PagingType.HEADER)
    data class Footer(val value: String) : PagingModel(PagingType.FOOTER)
    object Separator : PagingModel(PagingType.DIVIDER)
}
