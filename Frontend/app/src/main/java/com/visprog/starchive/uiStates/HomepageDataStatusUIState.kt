package com.visprog.starchive.uiStates

import com.visprog.starchive.models.GetBudgetResponse

interface HomepageDataStatusUIState {
    data class Success(val data: GetBudgetResponse): HomepageDataStatusUIState
    object Start: HomepageDataStatusUIState
    object Loading: HomepageDataStatusUIState
    data class Failed(val errorMessage: String): HomepageDataStatusUIState
}