package com.visprog.starchive.uiStates

interface StringDataStatusUIState {
    data class Success(val data: String): StringDataStatusUIState
    object Start: StringDataStatusUIState
    object Loading: StringDataStatusUIState
    data class Failed(val errorMessage: String): StringDataStatusUIState
}