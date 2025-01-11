

// package com.visprog.starchive.viewmodels

// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.setValue
// import androidx.lifecycle.ViewModel
// import androidx.lifecycle.ViewModelProvider
// import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
// import androidx.lifecycle.viewModelScope
// import androidx.lifecycle.viewmodel.initializer
// import androidx.lifecycle.viewmodel.viewModelFactory
// import com.visprog.starchive.StarchiveApplication
// import com.visprog.starchive.models.BannerModel
// import com.visprog.starchive.repositories.BannerRepository
// import com.visprog.starchive.repositories.UserGamesRepository
// import com.visprog.starchive.uiStates.PullsimDataStatusUIState
// import kotlinx.coroutines.flow.MutableStateFlow
// import kotlinx.coroutines.flow.StateFlow
// import kotlinx.coroutines.launch
// import retrofit2.Call
// import retrofit2.Callback
// import retrofit2.Response

// class PullsimViewModel(
//     private val bannerRepository: BannerRepository,
//     private val userGamesRepository: UserGamesRepository,
// ): ViewModel() {

//     private val _dataStatus = MutableStateFlow<PullsimDataStatusUIState>(PullsimDataStatusUIState.Start)
//     var dataStatus: PullsimDataStatusUIState by mutableStateOf(PullsimDataStatusUIState.Start)
//         private set

//     fun getBannersbyGameId(token: String, gameId: Int) {
//         viewModelScope.launch {
//             _dataStatus.value = PullsimDataStatusUIState.Loading
//             bannerRepository.getBannersByGameId(token, gameId).enqueue(object : Callback<List<BannerModel>> {
//                 override fun onResponse(call: Call<List<BannerModel>>, response: Response<List<BannerModel>>) {
//                     if (response.isSuccessful) {
//                         response.body()?.let {
//                             _dataStatus.value = PullsimDataStatusUIState.Success(it)
//                         } ?: run {
//                             _dataStatus.value = PullsimDataStatusUIState.Failed("No data available")
//                         }
//                     } else {
//                         _dataStatus.value = PullsimDataStatusUIState.Failed("Failed to get data")
//                     }
//                 }

//                 override fun onFailure(call: Call<List<BannerModel>>, t: Throwable) {
//                     _dataStatus.value = PullsimDataStatusUIState.Failed(t.message ?: "Unknown error")
//                 }
//             })
//         }
//     }

//     companion object {
//         val Factory: ViewModelProvider.Factory = viewModelFactory {
//             initializer {
//                 val application = (this[APPLICATION_KEY] as StarchiveApplication)
//                 val bannerRepository = application.container.bannerRepository
//                 val userGamesRepository = application.container.userGamesRepository
//                 PullsimViewModel(bannerRepository, userGamesRepository)
//             }
//         }
//     }

//     fun clearDataErrorMessage() {
//         dataStatus = PullsimDataStatusUIState.Start
//     }
// }

// package com.visprog.starchive.viewmodels

// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.setValue
// import androidx.lifecycle.ViewModel
// import androidx.lifecycle.ViewModelProvider
// import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
// import androidx.lifecycle.viewModelScope
// import androidx.lifecycle.viewmodel.initializer
// import androidx.lifecycle.viewmodel.viewModelFactory
// import com.visprog.starchive.StarchiveApplication
// import com.visprog.starchive.models.BannerModel
// import com.visprog.starchive.repositories.BannerRepository
// import com.visprog.starchive.repositories.UserGamesRepository
// import com.visprog.starchive.uiStates.PullsimDataStatusUIState
// import kotlinx.coroutines.flow.MutableStateFlow
// import kotlinx.coroutines.flow.StateFlow
// import kotlinx.coroutines.launch
// import retrofit2.Call
// import retrofit2.Callback
// import retrofit2.Response

// class PullsimViewModel(
//     private val bannerRepository: BannerRepository,
//     private val userGamesRepository: UserGamesRepository,
// ): ViewModel() {

//     private val _dataStatus = MutableStateFlow<PullsimDataStatusUIState>(PullsimDataStatusUIState.Start)
//     val dataStatus: StateFlow<PullsimDataStatusUIState> = _dataStatus

//     fun getBannersbyGameId(token: String, gameId: Int) {
//         viewModelScope.launch {
//             _dataStatus.value = PullsimDataStatusUIState.Loading
//             bannerRepository.getBannersByGameId(token, gameId).enqueue(object : Callback<List<BannerModel>> {
//                 override fun onResponse(call: Call<List<BannerModel>>, response: Response<List<BannerModel>>) {
//                     if (response.isSuccessful) {
//                         response.body()?.let {
//                             _dataStatus.value = PullsimDataStatusUIState.Success(it)
//                         } ?: run {
//                             _dataStatus.value = PullsimDataStatusUIState.Failed("No data available")
//                         }
//                     } else {
//                         _dataStatus.value = PullsimDataStatusUIState.Failed("Failed to get data")
//                     }
//                 }

//                 override fun onFailure(call: Call<List<BannerModel>>, t: Throwable) {
//                     _dataStatus.value = PullsimDataStatusUIState.Failed(t.message ?: "Unknown error")
//                 }
//             })
//         }
//     }

//     companion object {
//         val Factory: ViewModelProvider.Factory = viewModelFactory {
//             initializer {
//                 val application = (this[APPLICATION_KEY] as StarchiveApplication)
//                 val bannerRepository = application.container.bannerRepository
//                 val userGamesRepository = application.container.userGamesRepository
//                 PullsimViewModel(bannerRepository, userGamesRepository)
//             }
//         }
//     }

//     fun clearDataErrorMessage() {
//         _dataStatus.value = PullsimDataStatusUIState.Start
//     }
// }

package com.visprog.starchive.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.repositories.BannerRepository
import com.visprog.starchive.repositories.UserGamesRepository
import com.visprog.starchive.services.BannerResponse
import com.visprog.starchive.uiStates.PullsimDataStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullsimViewModel(
    private val bannerRepository: BannerRepository,
    private val userGamesRepository: UserGamesRepository,
): ViewModel() {

    private val _dataStatus = MutableStateFlow<PullsimDataStatusUIState>(PullsimDataStatusUIState.Start)
    val dataStatus: StateFlow<PullsimDataStatusUIState> = _dataStatus

    fun getBannersbyGameId(token: String, gameId: Int) {
        viewModelScope.launch {
            _dataStatus.value = PullsimDataStatusUIState.Loading
            bannerRepository.getBannersByGameId(token, gameId).enqueue(object : Callback<BannerResponse> {
                override fun onResponse(call: Call<BannerResponse>, response: Response<BannerResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _dataStatus.value = PullsimDataStatusUIState.Success(it.data)
                        } ?: run {
                            _dataStatus.value = PullsimDataStatusUIState.Failed("No data available")
                        }
                    } else {
                        _dataStatus.value = PullsimDataStatusUIState.Failed("Failed to get data")
                    }
                }

                override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                    _dataStatus.value = PullsimDataStatusUIState.Failed(t.message ?: "Unknown error")
                }
            })
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val bannerRepository = application.container.bannerRepository
                val userGamesRepository = application.container.userGamesRepository
                PullsimViewModel(bannerRepository, userGamesRepository)
            }
        }
    }

    fun clearDataErrorMessage() {
        _dataStatus.value = PullsimDataStatusUIState.Start
    }
}