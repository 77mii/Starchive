package com.visprog.starchive.uistates

import com.visprog.starchive.models.BudgetModel

interface HomepageDataStatusUIState {
    data class Success(val data: BudgetModel): HomepageDataStatusUIState
    object Start: HomepageDataStatusUIState
    object Loading: HomepageDataStatusUIState
    data class Failed(val errorMessage: String): HomepageDataStatusUIState
}