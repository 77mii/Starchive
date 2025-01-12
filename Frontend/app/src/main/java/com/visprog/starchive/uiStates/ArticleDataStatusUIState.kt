package com.visprog.starchive.uiStates

import com.visprog.starchive.models.GetAllArticleResponse

interface ArticleDataStatusUIState {
    data class Success(val data: GetAllArticleResponse): ArticleDataStatusUIState
    object Start: ArticleDataStatusUIState
    object Loading: ArticleDataStatusUIState
    data class Failed(val errorMessage: String): ArticleDataStatusUIState
}