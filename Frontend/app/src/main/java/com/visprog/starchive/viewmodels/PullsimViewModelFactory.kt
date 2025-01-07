package com.visprog.starchive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.visprog.starchive.models.HardPityModel

/*
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
}*/
class PullsimViewModelFactory(
    private val pullsimHistoryViewModel: PullsimHistoryViewModel,
    private val hardPityModel: HardPityModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PullsimViewModel::class.java)) {
            return PullsimViewModel(pullsimHistoryViewModel, hardPityModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}