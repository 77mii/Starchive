package com.visprog.starchive.models

data class GetAllArticleResponse(
    val data: List<ArticleModel>
)

data class GetArticleResponse(
    val data: ArticleModel
)

data class ArticleModel(
    val article_id: Int,
    val title: String,
    val text: String,
    val image_url: String?,
    val game_id: Int,
    val createdDate: String
)

data class ArticleRequest(
    val article_id: Int,
    val title: String,
    val text: String,
    val image_url: String?,
    val game_id: Int,
    val createdDate: String
)