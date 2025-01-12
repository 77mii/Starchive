package com.visprog.starchive.uiStates

import com.visprog.starchive.models.BannerModel
import kotlinx.coroutines.flow.StateFlow



sealed interface PullsimDataStatusUIState {
    data class Success(val data: List<BannerModel>) : PullsimDataStatusUIState
    object Loading : PullsimDataStatusUIState
    object Start : PullsimDataStatusUIState
    data class Failed(val errorMessage: String) : PullsimDataStatusUIState
}