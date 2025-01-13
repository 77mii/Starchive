package com.visprog.starchive.uiStates

import com.visprog.starchive.models.GetArticleResponse

interface ArticleDataStatusUIState {
    data class Success(val data: GetArticleResponse): ArticleDataStatusUIState
    object Start: ArticleDataStatusUIState
    object Loading: ArticleDataStatusUIState
    data class Failed(val errorMessage: String): ArticleDataStatusUIState
}