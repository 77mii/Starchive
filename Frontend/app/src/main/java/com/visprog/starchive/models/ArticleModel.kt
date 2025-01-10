package com.visprog.starchive.models

data class GetAllArticleResponse(
    val data: List<ArticleModel>
)

data class GetArticleResponse(
    val data: ArticleModel
)

data class ArticleModel(
    val articleId: Int,
    val title: String,
    val text: String,
    val imageUrl: String?,
    val gameId: Int,
    val createdDate: String
)

data class ArticleRequest(
    val title: String,
    val text: String,
    val imageUrl: String?,
    val gameId: Int,
    val createdDate: String
)