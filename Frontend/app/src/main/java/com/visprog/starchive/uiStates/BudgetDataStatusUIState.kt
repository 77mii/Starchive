package com.visprog.starchive.uiStates

import com.visprog.starchive.models.GetBudgetResponse

interface BudgetDataStatusUIState {
    data class Success(val data: GetBudgetResponse): BudgetDataStatusUIState
    object Start: BudgetDataStatusUIState 
    object Loading: BudgetDataStatusUIState
    data class Failed(val errorMessage: String): BudgetDataStatusUIState
}