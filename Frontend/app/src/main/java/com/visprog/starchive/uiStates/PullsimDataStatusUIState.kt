package com.visprog.starchive.uiStates

import com.visprog.starchive.models.BannerModel

/*interface PullsimDataStatusUIState {
    object Start : PullsimDataStatusUIState
    object Loading : PullsimDataStatusUIState
    object Success : PullsimDataStatusUIState
    object Error : PullsimDataStatusUIState
}*/

sealed interface PullsimDataStatusUIState {
    data class Success(val data: List<BannerModel>) : PullsimDataStatusUIState
    object Loading : PullsimDataStatusUIState
    object Start : PullsimDataStatusUIState
    data class Failed(val errorMessage: String) : PullsimDataStatusUIState
}