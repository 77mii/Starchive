package com.visprog.starchive.uiStates

import com.visprog.starchive.models.BannerModel

interface BannerDataStatusUIState {
    data class Success(val data: List<BannerModel>) : BannerDataStatusUIState
    object Loading : BannerDataStatusUIState
    object Start : BannerDataStatusUIState
    data class Failed(val errorMessage: String) : BannerDataStatusUIState
}
