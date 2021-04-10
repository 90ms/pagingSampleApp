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

    private val flow = Pager(PagingConfig(pageSize = BaseConst.DEFAULT_PAGING_COUNT)) {
        SamplePagingSource(PagingRepository())
    }.flow.map {
        it.map<PagingModel> { PagingModel.Item(it) }
            .insertHeaderItem(PagingModel.Header("헤더에요"))
            .insertFooterItem(PagingModel.Footer("바닥이에요"))
            .insertSeparators { before, after ->
                when {
                    before is PagingModel.Item && after is PagingModel.Item -> {
                        if (before.value.toInt() % 10 == 0) PagingModel.Separator
                        else null
                    }
                    else -> null
                }
            }
    }.cachedIn(viewModelScope)

    val paging: MutableLiveData<PagingData<PagingModel>> = MutableLiveData()

    fun init() {
        viewModelScope.launch {
            flow.collectLatest { pagingData ->
                paging.postValue(pagingData)
            }
        }
    }
}