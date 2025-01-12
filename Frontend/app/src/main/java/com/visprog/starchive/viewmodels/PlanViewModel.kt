package com.visprog.starchive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.models.PlanModel
import com.visprog.starchive.models.PlanRequest
import com.visprog.starchive.repositories.PlanRepository
import com.visprog.starchive.uiStates.PlansDataStatusUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlanViewModel(
    private val planRepository: PlanRepository
) : ViewModel() {
    private val _plansDataStatus = MutableStateFlow<PlansDataStatusUIState>(PlansDataStatusUIState.Start)
    val plansDataStatus: StateFlow<PlansDataStatusUIState> = _plansDataStatus

    fun getPlans(token: String) {
        viewModelScope.launch {
            _plansDataStatus.value = PlansDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response = planRepository.getPlansByUserId(token).execute()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("PlanViewModel", "Plans fetched successfully: $it")
                            _plansDataStatus.value = PlansDataStatusUIState.Success(it)
                        } ?: run {
                            Log.d("PlanViewModel", "No data available")
                            _plansDataStatus.value = PlansDataStatusUIState.Failed("No data available")
                        }
                    } else {
                        Log.d("PlanViewModel", "Failed to fetch data: ${response.errorBody()?.string()}")
                        _plansDataStatus.value = PlansDataStatusUIState.Failed("Failed to fetch data")
                    }
                }
            } catch (e: Exception) {
                _plansDataStatus.value = PlansDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun createPlan(token: String, plan: PlanRequest) {
        viewModelScope.launch {
            _plansDataStatus.value = PlansDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response = planRepository.createPlan(token, plan).execute()
                    if (response.isSuccessful) {
                        getPlans(token)
                    } else {
                        Log.e("PlanViewModel", "Failed to create plan: ${response.errorBody()?.string()}")
                        _plansDataStatus.value = PlansDataStatusUIState.Failed("Failed to create plan")
                    }
                }
            } catch (e: Exception) {
                Log.e("PlanViewModel", "Error creating plan", e)
                _plansDataStatus.value = PlansDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun deletePlan(token: String, planId: Int) {
        viewModelScope.launch {
            _plansDataStatus.value = PlansDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response = planRepository.deletePlan(token, planId).execute()
                    if (response.isSuccessful) {
                        getPlans(token)
                    } else {
                        Log.e("PlanViewModel", "Failed to delete plan: ${response.errorBody()?.string()}")
                        _plansDataStatus.value = PlansDataStatusUIState.Failed("Failed to delete plan")
                    }
                }
            } catch (e: Exception) {
                Log.e("PlanViewModel", "Error deleting plan", e)
                _plansDataStatus.value = PlansDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val planRepository = application.container.planRepository
                PlanViewModel(planRepository)
            }
        }
    }
}