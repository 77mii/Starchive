package com.visprog.starchive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.models.BudgetModel
import com.visprog.starchive.repositories.BudgetRepository
import com.visprog.starchive.uiStates.BudgetDataStatusUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BudgetingViewModel(private val budgetRepository: BudgetRepository) : ViewModel() {
    private val _dataStatus =
            MutableStateFlow<BudgetDataStatusUIState>(BudgetDataStatusUIState.Start)
    val dataStatus: StateFlow<BudgetDataStatusUIState> = _dataStatus

    fun getBudgets(token: String, gameId: Int) {
        viewModelScope.launch {
            _dataStatus.value = BudgetDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response =
                            budgetRepository.getBudgetByUserIdAndGameId(token, gameId).execute()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _dataStatus.value = BudgetDataStatusUIState.Success(it)
                        }
                                ?: run {
                                    _dataStatus.value =
                                            BudgetDataStatusUIState.Failed("No data available")
                                }
                    } else {
                        _dataStatus.value = BudgetDataStatusUIState.Failed("Failed to fetch data")
                    }
                }
            } catch (e: Exception) {
                _dataStatus.value = BudgetDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun updateBudget(budgetId: Int, token: String, budget: BudgetModel) {
        viewModelScope.launch {
            _dataStatus.value = BudgetDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response = budgetRepository.updateBudget(budgetId, budget).execute()
                    if (response.isSuccessful) {
                        getBudgets(token, budget.gameId)
                    } else {
                        _dataStatus.value =
                                BudgetDataStatusUIState.Failed("Failed to update budget")
                    }
                }
            } catch (e: Exception) {
                _dataStatus.value = BudgetDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val budgetRepository = application.container.budgetRepository
                BudgetingViewModel(budgetRepository)
            }
        }
    }
}
