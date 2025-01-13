package com.visprog.starchive.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.visprog.starchive.uiStates.ArticleDataStatusUIState
import com.visprog.starchive.viewmodels.ArticleViewModel

@Composable
fun ArticleView(
    articleViewModel: ArticleViewModel = viewModel(factory = ArticleViewModel.Factory),
    token: String,
    articleId: Int,
) {
    val articleDataStatus = articleViewModel.articleDataStatus.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        articleViewModel.getArticle(token = token, articleId = articleId)
    }

    when (articleDataStatus.value) {
        is ArticleDataStatusUIState.Success -> {
            val articleData = (articleDataStatus.value as ArticleDataStatusUIState.Success).data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
            ) {
                Text(
                    text = articleData.data.title,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 32.sp
                )
                Text(
                    text = articleData.data.text,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp
                )
            }
        }

        is ArticleDataStatusUIState.Failed -> {
            Text(
                text = "Failed to load article data",
                color = Color.Red
            )
        }
    }
}