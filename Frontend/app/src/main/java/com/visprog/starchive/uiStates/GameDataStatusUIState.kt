package com.visprog.starchive.uiStates

import com.visprog.starchive.models.GetGameResponse

interface GameDataStatusUIState {
    data class Success(val data: GetGameResponse): GameDataStatusUIState
    object Start: GameDataStatusUIState
    object Loading: GameDataStatusUIState
    data class Failed(val errorMessage: String): GameDataStatusUIState
}