package com.example.paging3sample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paging3sample.util.BaseConst
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PagingViewModel : ViewModel() {

    private val flow = Pager(PagingConfig(pageSize = BaseConst.DEFAULT_PAGING_COUNT)) {
        DooolPagingSource(PagingRepository())
    }.flow.map {
        it.map<PagingModel> { PagingModel.Item(it) }
            .insertHeaderItem(PagingModel.Header("====HEADER===="))
            .insertFooterItem(PagingModel.Footer("====FOOTER===="))
            .insertSeparators { before, after ->
                when {
                    before is PagingModel.Item && after is PagingModel.Item -> {
                        if (before.value.startsWith("C") && after.value.startsWith("A")) PagingModel.Separator
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