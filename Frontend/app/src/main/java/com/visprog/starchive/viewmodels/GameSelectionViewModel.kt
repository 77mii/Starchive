

package com.visprog.starchive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.models.GameModel
import com.visprog.starchive.repositories.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameSelectionViewModel(
    private val gameRepository: GameRepository
): ViewModel() {

    private val _games = MutableStateFlow<List<GameModel>>(emptyList())
    val games: StateFlow<List<GameModel>> = _games

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            gameRepository.getAllGames().enqueue(object : Callback<List<GameModel>> {
                override fun onResponse(call: Call<List<GameModel>>, response: Response<List<GameModel>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _games.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<List<GameModel>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val gameRepository = application.container.gameRepository
                GameSelectionViewModel(gameRepository)
            }
        }
    }
}