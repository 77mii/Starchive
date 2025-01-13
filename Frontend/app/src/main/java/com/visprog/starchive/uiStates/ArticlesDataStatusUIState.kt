package com.visprog.starchive.uiStates

import com.visprog.starchive.models.GetAllArticleResponse

interface ArticlesDataStatusUIState {
    data class Success(val data: GetAllArticleResponse): ArticlesDataStatusUIState
    object Start: ArticlesDataStatusUIState
    object Loading: ArticlesDataStatusUIState
    data class Failed(val errorMessage: String): ArticlesDataStatusUIState
}