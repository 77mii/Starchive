package com.visprog.starchive.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.enums.PrioritiesEnum
import com.visprog.starchive.models.ErrorModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.repositories.ArticleRepository
import com.visprog.starchive.repositories.BannerRepository
import com.visprog.starchive.repositories.BudgetRepository
import com.visprog.starchive.repositories.GameRepository
import com.visprog.starchive.repositories.UserRepository
import com.visprog.starchive.uiStates.ArticlesDataStatusUIState
import com.visprog.starchive.uiStates.BannerDataStatusUIState
import com.visprog.starchive.uiStates.BudgetDataStatusUIState
import com.visprog.starchive.uiStates.GameDataStatusUIState
import com.visprog.starchive.uiStates.StringDataStatusUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HomepageViewModel(
        private val budgetRepository: BudgetRepository,
        private val articleRepository: ArticleRepository,
        private val bannerRepository: BannerRepository,
        private val gameRepository: GameRepository,
        private val userRepository: UserRepository
) : ViewModel() {
    private val _budgetDataStatus =
            MutableStateFlow<BudgetDataStatusUIState>(BudgetDataStatusUIState.Start)
    val budgetDataStatus: StateFlow<BudgetDataStatusUIState> = _budgetDataStatus

    private val _articleDataStatus =
            MutableStateFlow<ArticlesDataStatusUIState>(ArticlesDataStatusUIState.Start)
    val articleDataStatus: StateFlow<ArticlesDataStatusUIState> = _articleDataStatus

    private val _bannerDataStatus =
            MutableStateFlow<BannerDataStatusUIState>(BannerDataStatusUIState.Start)
    val bannerDataStatus: StateFlow<BannerDataStatusUIState> = _bannerDataStatus

    private val _gameDataStatus = MutableStateFlow<GameDataStatusUIState>(GameDataStatusUIState.Start)
    val gameDataStatus: StateFlow<GameDataStatusUIState> = _gameDataStatus

    var logoutStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    val token: StateFlow<String> =
            userRepository.currentUserToken.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = ""
            )

    fun getBudgets(token: String, gameId: Int) {
        viewModelScope.launch {
            _budgetDataStatus.value = BudgetDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response =
                            budgetRepository.getBudgetByUserIdAndGameId(token, gameId).execute()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _budgetDataStatus.value = BudgetDataStatusUIState.Success(it)
                        }
                                ?: run {
                                    _budgetDataStatus.value =
                                            BudgetDataStatusUIState.Failed("No data available")
                                }
                    } else {
                        _budgetDataStatus.value = BudgetDataStatusUIState.Failed("Failed to fetch data")
                    }
                }
            } catch (e: Exception) {
                _budgetDataStatus.value = BudgetDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun getArticles(token: String, gameId: Int) {
        viewModelScope.launch {
            _articleDataStatus.value = ArticlesDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response =
                            articleRepository.getArticlesByGameId(token, gameId).execute()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("HomepageViewModel", "Articles fetched successfully: $it")
                            _articleDataStatus.value = ArticlesDataStatusUIState.Success(it)
                        } ?: run {
                            Log.d("HomepageViewModel", "No data available")
                            _articleDataStatus.value = ArticlesDataStatusUIState.Failed("No data available")
                        }
                    } else {
                        Log.d("HomepageViewModel", "Failed to fetch data: ${response.errorBody()?.string()}")
                        _articleDataStatus.value = ArticlesDataStatusUIState.Failed("Failed to fetch data")
                    }
                }
            } catch (e: Exception) {
                _articleDataStatus.value = ArticlesDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun getBanners(token: String, gameId: Int) {
        viewModelScope.launch {
            _bannerDataStatus.value = BannerDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response =
                            bannerRepository.getBannersByGameId(token, gameId).execute()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("HomepageViewModel", "Banners fetched successfully: $it")
                            _bannerDataStatus.value = BannerDataStatusUIState.Success(it.data)
                        } ?: run {
                            Log.d("HomepageViewModel", "No data available")
                            _bannerDataStatus.value = BannerDataStatusUIState.Failed("No data available")
                        }
                    } else {
                        Log.d("HomepageViewModel", "Failed to fetch data: ${response.errorBody()?.string()}")
                        _bannerDataStatus.value = BannerDataStatusUIState.Failed("Failed to fetch data")
                    }
                }
            } catch (e: Exception) {
                _bannerDataStatus.value = BannerDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun getGame(token: String, gameId: Int) {
        viewModelScope.launch {
            _gameDataStatus.value = GameDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response = gameRepository.getGameById(token, gameId).execute()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _gameDataStatus.value = GameDataStatusUIState.Success(it)
                        }
                            ?: run {
                                _gameDataStatus.value =
                                    GameDataStatusUIState.Failed("No data available")
                            }
                    } else {
                        _gameDataStatus.value = GameDataStatusUIState.Failed("Failed to fetch data")
                    }
                }
            } catch (e: Exception) {
                _gameDataStatus.value = GameDataStatusUIState.Failed(e.message ?: "Unknown error")
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
                val bannerRepository = application.container.bannerRepository
                val gameRepository = application.container.gameRepository
                HomepageViewModel(budgetRepository, articleRepository, bannerRepository, gameRepository, userRepository)
            }
        }
    }

    fun convertStringToEnum(text: String): PrioritiesEnum {
        return when (text) {
            "High" -> PrioritiesEnum.High
            "Medium" -> PrioritiesEnum.Medium
            else -> PrioritiesEnum.Low
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
        _budgetDataStatus.value = BudgetDataStatusUIState.Start
    }

    fun logoutUser(token: String, navController: NavHostController) {
        viewModelScope.launch {
            logoutStatus = StringDataStatusUIState.Loading

            Log.d("token-logout", "LOGOUT TOKEN: ${token}")

            try {
                val call = userRepository.logout(token)

                call.enqueue(
                        object : Callback<GeneralResponseModel> {
                            override fun onResponse(
                                    call: Call<GeneralResponseModel>,
                                    res: Response<GeneralResponseModel>
                            ) {
                                if (res.isSuccessful) {
                                    logoutStatus =
                                            StringDataStatusUIState.Success(
                                                    data = res.body()!!.data
                                            )

                                    saveUsernameToken("Unknown", "Unknown")

                                    navController.navigate(PagesEnum.Login.name) {
                                        popUpTo(PagesEnum.Homepage.name) { inclusive = true }
                                    }
                                } else {
                                    val errorMessage =
                                            Gson().fromJson(
                                                            res.errorBody()!!.charStream(),
                                                            ErrorModel::class.java
                                                    )

                                    logoutStatus =
                                            StringDataStatusUIState.Failed(errorMessage.errors)
                                    // set error message toast
                                }
                            }

                            override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                                logoutStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                                Log.d("logout-failure", t.localizedMessage)
                            }
                        }
                )
            } catch (error: IOException) {
                logoutStatus = StringDataStatusUIState.Failed(error.localizedMessage)
                Log.d("logout-error", error.localizedMessage)
            }
        }
    }
}