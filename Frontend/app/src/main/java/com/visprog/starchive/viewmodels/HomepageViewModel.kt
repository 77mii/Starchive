package com.visprog.starchive.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.enums.PrioritiesEnum
import com.visprog.starchive.repositories.ArticleRepository
import com.visprog.starchive.repositories.BudgetRepository
import com.visprog.starchive.repositories.UserRepository
import com.visprog.starchive.uiStates.HomepageDataStatusUIState
import com.visprog.starchive.uiStates.HomepageUIState
import com.visprog.starchive.uiStates.StringDataStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomepageViewModel (
    private val budgetRepository: BudgetRepository,
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _homepageUIState = MutableLiveData<HomepageUIState>()

    private val _dataStatus = MutableStateFlow<HomepageDataStatusUIState>(HomepageDataStatusUIState.Start)
    val dataStatus: StateFlow<HomepageDataStatusUIState> = _dataStatus

    var logoutStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    fun getArticles(gameId: Int) {
        articleRepository.getArticlesByGameId(gameId)
    }

    val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    fun getBudgets(token: String, gameId: Int) {
        viewModelScope.launch {
            _dataStatus.value = HomepageDataStatusUIState.Loading
            try {
                val response = budgetRepository.getBudgetByUserIdAndGameId(token, gameId).execute()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _dataStatus.value = HomepageDataStatusUIState.Success(it)
                    } ?: run {
                        _dataStatus.value = HomepageDataStatusUIState.Failed("No data available")
                    }
                } else {
                    _dataStatus.value = HomepageDataStatusUIState.Failed("Failed to fetch data")
                }
            } catch (e: Exception) {
                _dataStatus.value = HomepageDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val articleRepository = application.container.articleRepository
                val budgetRepository = application.container.budgetRepository
                val userRepository = application.container.userRepository
                HomepageViewModel(budgetRepository, articleRepository, userRepository)
            }
        }
    }

    fun convertStringToEnum(text: String): PrioritiesEnum {
        if (text == "High") {
            return PrioritiesEnum.High
        } else if (text == "Medium") {
            return PrioritiesEnum.Medium
        } else {
            return PrioritiesEnum.Low
        }
    }

    fun saveUsernameToken(token: String, username: String) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveUsername(username)
        }
    }

    fun clearLogoutErrorMessage() {
        logoutStatus = StringDataStatusUIState.Start
    }

    fun clearDataErrorMessage() {
        _dataStatus.value = HomepageDataStatusUIState.Start
    }
}