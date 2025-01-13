package com.visprog.starchive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.visprog.starchive.StarchiveApplication
import com.visprog.starchive.repositories.ArticleRepository
import com.visprog.starchive.uiStates.ArticleDataStatusUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _articleDataStatus =
        MutableStateFlow<ArticleDataStatusUIState>(ArticleDataStatusUIState.Start)
    val articleDataStatus: StateFlow<ArticleDataStatusUIState> = _articleDataStatus

    fun getArticle(token: String, articleId: Int) {
        viewModelScope.launch {
            _articleDataStatus.value = ArticleDataStatusUIState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val response =
                        articleRepository.getArticleById(token, articleId).execute()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("HomepageViewModel", "Articles fetched successfully: $it")
                            _articleDataStatus.value = ArticleDataStatusUIState.Success(it)
                        } ?: run {
                            Log.d("HomepageViewModel", "No data available")
                            _articleDataStatus.value = ArticleDataStatusUIState.Failed("No data available")
                        }
                    } else {
                        Log.d("HomepageViewModel", "Failed to fetch data: ${response.errorBody()?.string()}")
                        _articleDataStatus.value = ArticleDataStatusUIState.Failed("Failed to fetch data")
                    }
                }
            } catch (e: Exception) {
                _articleDataStatus.value = ArticleDataStatusUIState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StarchiveApplication)
                val articleRepository = application.container.articleRepository
                ArticleViewModel(articleRepository)
            }
        }
    }
}