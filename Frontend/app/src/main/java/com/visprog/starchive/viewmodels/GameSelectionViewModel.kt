

package com.visprog.starchive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.models.GameModel
import com.visprog.starchive.repositories.GameRepository
import com.visprog.starchive.repositories.UserRepository
import com.visprog.starchive.services.GameResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameSelectionViewModel(
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _games = MutableStateFlow<List<GameModel>>(emptyList())
    val games: StateFlow<List<GameModel>> = _games

    val token: StateFlow<String> =
        userRepository.currentUserToken.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            token.collect { currentToken ->
                if (currentToken.isNotEmpty()) {
                    gameRepository.getAllGames(currentToken).enqueue(object : Callback<GameResponse> {
                        override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    Log.d("GameSelectionViewModel", "Data received: ${it.data}")
                                    _games.value = it.data
                                }
                            } else {
                                Log.d("GameSelectionViewModel", "Failed to fetch games: ${response.errorBody()?.string()}")
                            }
                        }

                        override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                            Log.d("GameSelectionViewModel", "Error: ${t.message}")
                            t.printStackTrace()
                        }
                    })
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val gameRepository = application.container.gameRepository
                val userRepository = application.container.userRepository

                GameSelectionViewModel(gameRepository, userRepository)
            }
        }
    }
}