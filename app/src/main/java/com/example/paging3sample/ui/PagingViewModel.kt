package com.example.paging3sample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paging3sample.adapter.SamplePagingSource
import com.example.paging3sample.data.PagingModel
import com.example.paging3sample.repository.PagingRepository
import com.example.paging3sample.util.BaseConst
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PagingViewModel : ViewModel() {

    val paging: MutableLiveData<PagingData<PagingModel>> = MutableLiveData()

    private val pagingCollection = Pager(
        PagingConfig(BaseConst.DEFAULT_PAGING_COUNT)
    ) { SamplePagingSource(PagingRepository()) }.flow.map { pagingData ->
        pagingData.map<PagingModel> { item ->
            PagingModel.Item(item)
        }
            .insertHeaderItem(
                PagingModel.Header("헤더에요")
            )
            .insertFooterItem(
                PagingModel.Footer("바닥이에요")
            )
            .insertSeparators { before, after ->
                if (before is PagingModel.Item && after is PagingModel.Item) {
                    if (before.value.toInt() % 10 == 0) PagingModel.Separator
                    else null
                } else {
                    null
                }
            }
    }.cachedIn(viewModelScope)

    fun fetchData() {
        viewModelScope.launch {
            pagingCollection.collectLatest { pagingData ->
                paging.postValue(pagingData)
            }
        }
    }
}