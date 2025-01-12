package com.visprog.starchive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.models.BudgetModel
import com.visprog.starchive.models.GameModel
import com.visprog.starchive.repositories.BudgetRepository
import com.visprog.starchive.repositories.GameRepository
import com.visprog.starchive.uiStates.BudgetDataStatusUIState
import com.visprog.starchive.uiStates.GameDataStatusUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BudgetingViewModel(
    private val budgetRepository: BudgetRepository,
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _dataStatus = MutableStateFlow<BudgetDataStatusUIState>(BudgetDataStatusUIState.Start)
    val dataStatus: StateFlow<BudgetDataStatusUIState> = _dataStatus

    private val _gameData = MutableStateFlow<GameDataStatusUIState>(GameDataStatusUIState.Start)
    val gameData: StateFlow<GameDataStatusUIState> = _gameData

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

    fun updateBudget(token: String, budgetId: Int, budget: BudgetModel) {
        viewModelScope.launch {
            _dataStatus.value = BudgetDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    Log.d("BudgetingViewModel", "Updating budget with ID: $budgetId")
                    val response = budgetRepository.updateBudget(token, budgetId, budget).execute()
                    if (response.isSuccessful) {
                        Log.d("BudgetingViewModel", "Budget updated successfully")
                        // Wait for update to complete before refreshing
                        delay(500)
                        getBudgets(token, budget.gameId)
                    } else {
                        Log.e("BudgetingViewModel", "Failed to update budget: ${response.errorBody()?.string()}")
                        _dataStatus.value = BudgetDataStatusUIState.Failed("Failed to update budget: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("BudgetingViewModel", "Error updating budget", e)
                _dataStatus.value = BudgetDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun getGame(token: String, gameId: Int) {
        viewModelScope.launch {
            _gameData.value = GameDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response = gameRepository.getGameById(token, gameId).execute()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _gameData.value = GameDataStatusUIState.Success(it)
                        }
                                ?: run {
                                    _gameData.value =
                                            GameDataStatusUIState.Failed("No data available")
                                }
                    } else {
                        _gameData.value = GameDataStatusUIState.Failed("Failed to fetch data")
                    }
                }
            } catch (e: Exception) {
                _gameData.value = GameDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val budgetRepository = application.container.budgetRepository
                val gameRepository = application.container.gameRepository
                BudgetingViewModel(budgetRepository, gameRepository)
            }
        }
    }
}
