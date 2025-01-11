

// package com.visprog.starchive.viewmodels

// import android.util.Log
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.setValue
// import androidx.lifecycle.MutableLiveData
// import androidx.lifecycle.ViewModel
// import androidx.lifecycle.ViewModelProvider
// import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
// import androidx.lifecycle.viewModelScope
// import androidx.lifecycle.viewmodel.initializer
// import androidx.lifecycle.viewmodel.viewModelFactory
// import androidx.navigation.NavHostController
// import com.google.gson.Gson
// import com.visprog.starchive.StarchiveApplication
// import com.visprog.starchive.enums.PagesEnum
// import com.visprog.starchive.enums.PrioritiesEnum
// import com.visprog.starchive.models.ErrorModel
// import com.visprog.starchive.models.GeneralResponseModel
// import com.visprog.starchive.repositories.ArticleRepository
// import com.visprog.starchive.repositories.BudgetRepository
// import com.visprog.starchive.repositories.UserRepository
// import com.visprog.starchive.uiStates.HomepageDataStatusUIState
// import com.visprog.starchive.uiStates.HomepageUIState
// import com.visprog.starchive.uiStates.StringDataStatusUIState
// import kotlinx.coroutines.flow.MutableStateFlow
// import kotlinx.coroutines.flow.SharingStarted
// import kotlinx.coroutines.flow.StateFlow
// import kotlinx.coroutines.flow.stateIn
// import kotlinx.coroutines.launch
// import retrofit2.Call
// import retrofit2.Callback
// import retrofit2.Response
// import java.io.IOException

// class HomepageViewModel (
//     private val budgetRepository: BudgetRepository,
//     private val articleRepository: ArticleRepository,
//     private val userRepository: UserRepository
// ): ViewModel() {
//     private val _homepageUIState = MutableLiveData<HomepageUIState>()

//     private val _dataStatus = MutableStateFlow<HomepageDataStatusUIState>(HomepageDataStatusUIState.Start)
//     val dataStatus: StateFlow<HomepageDataStatusUIState> = _dataStatus

//     var logoutStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
//         private set

//     fun getArticles(gameId: Int) {
//         articleRepository.getArticlesByGameId(gameId)
//     }

//     val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
//         scope = viewModelScope,
//         started = SharingStarted.WhileSubscribed(5000),
//         initialValue = ""
//     )

//     fun getBudgets(token: String, gameId: Int) {
//         viewModelScope.launch {
//             _dataStatus.value = HomepageDataStatusUIState.Loading
//             try {
//                 val response = budgetRepository.getBudgetByUserIdAndGameId(token, gameId).execute()
//                 if (response.isSuccessful) {
//                     response.body()?.let {
//                         _dataStatus.value = HomepageDataStatusUIState.Success(it)
//                     } ?: run {
//                         _dataStatus.value = HomepageDataStatusUIState.Failed("No data available")
//                     }
//                 } else {
//                     _dataStatus.value = HomepageDataStatusUIState.Failed("Failed to fetch data")
//                 }
//             } catch (e: Exception) {
//                 _dataStatus.value = HomepageDataStatusUIState.Failed(e.message ?: "Unknown error")
//             }
//         }
//     }

    

//     companion object {
//         val Factory: ViewModelProvider.Factory = viewModelFactory {
//             initializer {
//                 val application = (this[APPLICATION_KEY] as StarchiveApplication)
//                 val articleRepository = application.container.articleRepository
//                 val budgetRepository = application.container.budgetRepository
//                 val userRepository = application.container.userRepository
//                 HomepageViewModel(budgetRepository, articleRepository, userRepository)
//             }
//         }
//     }

//     fun convertStringToEnum(text: String): PrioritiesEnum {
//         return when (text) {
//             "High" -> PrioritiesEnum.High
//             "Medium" -> PrioritiesEnum.Medium
//             else -> PrioritiesEnum.Low
//         }
//     }

//     fun saveUsernameToken(token: String, username: String) {
//         viewModelScope.launch {
//             userRepository.saveUserToken(token)
//             userRepository.saveUsername(username)
//         }
//     }

//     fun clearLogoutErrorMessage() {
//         logoutStatus = StringDataStatusUIState.Start
//     }

//     fun clearDataErrorMessage() {
//         _dataStatus.value = HomepageDataStatusUIState.Start
//     }

//     fun logoutUser(token: String, navController: NavHostController) {
//         viewModelScope.launch {
//             logoutStatus = StringDataStatusUIState.Loading

//             Log.d("token-logout", "LOGOUT TOKEN: ${token}")

//             try {
//                 val call = userRepository.logout(token)

//                 call.enqueue(object: Callback<GeneralResponseModel> {
//                     override fun onResponse(call: Call<GeneralResponseModel>, res: Response<GeneralResponseModel>) {
//                         if (res.isSuccessful) {
//                             logoutStatus = StringDataStatusUIState.Success(data = res.body()!!.data)

//                             saveUsernameToken("Unknown", "Unknown")

//                             navController.navigate(PagesEnum.Login.name) {
//                                 popUpTo(PagesEnum.Home.name) {
//                                     inclusive = true
//                                 }
//                             }
//                         } else {
//                             val errorMessage = Gson().fromJson(
//                                 res.errorBody()!!.charStream(),
//                                 ErrorModel::class.java
//                             )

//                             logoutStatus = StringDataStatusUIState.Failed(errorMessage.errors)
//                             // set error message toast
//                         }
//                     }

//                     override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
//                         logoutStatus = StringDataStatusUIState.Failed(t.localizedMessage)
//                         Log.d("logout-failure", t.localizedMessage)
//                     }
//                 })
//             } catch (error: IOException) {
//                 logoutStatus = StringDataStatusUIState.Failed(error.localizedMessage)
//                 Log.d("logout-error", error.localizedMessage)
//             }
//         }
//     }
// }

package com.visprog.starchive.viewmodels

import android.util.Log
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
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.enums.PagesEnum
import com.visprog.starchive.enums.PrioritiesEnum
import com.visprog.starchive.models.BudgetModel
import com.visprog.starchive.models.ErrorModel
import com.visprog.starchive.models.GeneralResponseModel
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

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
            budgetRepository.getBudgetByUserIdAndGameId(token, gameId).enqueue(object : Callback<BudgetModel> {
                override fun onResponse(call: Call<BudgetModel>, response: Response<BudgetModel>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _dataStatus.value = HomepageDataStatusUIState.Success(it)
                        } ?: run {
                            _dataStatus.value = HomepageDataStatusUIState.Failed("No data available")
                        }
                    } else {
                        _dataStatus.value = HomepageDataStatusUIState.Failed("Failed to fetch data")
                    }
                }

                override fun onFailure(call: Call<BudgetModel>, t: Throwable) {
                    _dataStatus.value = HomepageDataStatusUIState.Failed(t.message ?: "Unknown error")
                }
            })
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
        _dataStatus.value = HomepageDataStatusUIState.Start
    }

    fun logoutUser(token: String, navController: NavHostController) {
        viewModelScope.launch {
            logoutStatus = StringDataStatusUIState.Loading

            Log.d("token-logout", "LOGOUT TOKEN: ${token}")

            try {
                val call = userRepository.logout(token)

                call.enqueue(object: Callback<GeneralResponseModel> {
                    override fun onResponse(call: Call<GeneralResponseModel>, res: Response<GeneralResponseModel>) {
                        if (res.isSuccessful) {
                            logoutStatus = StringDataStatusUIState.Success(data = res.body()!!.data)

                            saveUsernameToken("Unknown", "Unknown")

                            navController.navigate(PagesEnum.Login.name) {
                                popUpTo(PagesEnum.Home.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            logoutStatus = StringDataStatusUIState.Failed(errorMessage.errors)
                            // set error message toast
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        logoutStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                        Log.d("logout-failure", t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                logoutStatus = StringDataStatusUIState.Failed(error.localizedMessage)
                Log.d("logout-error", error.localizedMessage)
            }
        }
    }
}