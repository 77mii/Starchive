package com.visprog.starchive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PullsimViewModelFactory(
    private val pullsimHistoryViewModel: PullsimHistoryViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PullsimViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PullsimViewModel(pullsimHistoryViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}