package com.example.paging3sample.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PagingRepository {

    suspend fun getPagingData(page: Int): Pair<List<String>, Int?> {
        return withContext(Dispatchers.IO) {
            listDataPaging(page)
        }
    }

    fun getPagingData1(page: Int): Pair<List<String>, Int?> {
        return if (page <= 30) listDataPaging(page) else Pair(listOf(), null)
    }

    private fun listDataPaging(page: Int): Pair<List<String>, Int?> =
        Pair(listOf("$page"), page + 1)
}