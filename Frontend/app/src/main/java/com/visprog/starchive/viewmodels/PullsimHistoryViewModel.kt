package com.visprog.starchive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visprog.starchive.views.PullResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PullsimHistoryViewModel : ViewModel() {
    private val _pullLogs = MutableStateFlow<List<PullResult>>(emptyList())
    val pullLogs: StateFlow<List<PullResult>> = _pullLogs

    fun addPullResult(result: PullResult) {
        viewModelScope.launch {
            _pullLogs.value = _pullLogs.value + result
        }
    }
}