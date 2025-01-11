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
import com.visprog.starchive.repositories.BannerRepository
import com.visprog.starchive.repositories.UserGamesRepository
import com.visprog.starchive.uiStates.PullsimDataStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PullsimViewModel(
    private val bannerRepository: BannerRepository,
    private val userGamesRepository: UserGamesRepository,

    ): ViewModel() {

    private val _dataStatus = MutableStateFlow<PullsimDataStatusUIState>(PullsimDataStatusUIState.Start)
   /* var dataStatus: StateFlow<PullsimDataStatusUIState> = _dataStatus*/
 var dataStatus: PullsimDataStatusUIState by mutableStateOf(PullsimDataStatusUIState.Start)
        private set

    /*fun getBanners( gameId: Int) {
       viewModelScope.launch {
           _dataStatus.value = PullsimDataStatusUIState.Loading
           try {
               val response = bannerRepository.getBannersByGameId(gameId).execute()
               if (response.isSuccessful) {
                   response.body()?.let {
                       _dataStatus.value = PullsimDataStatusUIState.Success(it)
                   } ?: run {
                       _dataStatus.value = PullsimDataStatusUIState.Failed("No data available")
                   }
               } else {
                   _dataStatus.value = PullsimDataStatusUIState.Failed("Failed to get data")
               }
           } catch (e: Exception) {
               _dataStatus.value = PullsimDataStatusUIState.Failed(e.message ?: "Unknown error")
           }
       }
    }*/

    fun getBannersbyGameId(gameId: Int) {
        viewModelScope.launch {
            _dataStatus.value = PullsimDataStatusUIState.Loading
            try {
                val response = bannerRepository.getBannersByGameId(gameId).execute()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _dataStatus.value = PullsimDataStatusUIState.Success(it)
                    } ?: run {
                        _dataStatus.value = PullsimDataStatusUIState.Failed("No data available")
                    }
                } else {
                    _dataStatus.value = PullsimDataStatusUIState.Failed("Failed to get data")
                }
            } catch (e: Exception) {
                _dataStatus.value = PullsimDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
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
        dataStatus = PullsimDataStatusUIState.Start
    }
}
