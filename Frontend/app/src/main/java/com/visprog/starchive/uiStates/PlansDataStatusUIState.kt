package com.visprog.starchive.uiStates

import com.visprog.starchive.models.GetAllPlanResponse

interface PlansDataStatusUIState {
    data class Success(val data: GetAllPlanResponse): PlansDataStatusUIState
    object Start: PlansDataStatusUIState
    object Loading: PlansDataStatusUIState
    data class Failed(val errorMessage: String): PlansDataStatusUIState
}