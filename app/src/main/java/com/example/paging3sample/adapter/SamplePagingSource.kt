package com.example.paging3sample.adapter

import androidx.paging.PagingSource
import com.example.paging3sample.repository.PagingRepository
import timber.log.Timber

class SamplePagingSource(
    private val repository: PagingRepository
) : PagingSource<Int, String>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val nextPage = params.key ?: 1
            val response = repository.getPagingData(nextPage)
//            val response = repository.getPagingData1(nextPage)

            LoadResult.Page(
                data = response.first,
                prevKey = null,
                nextKey = response.second
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(Throwable(e.message))
        }
    }
}