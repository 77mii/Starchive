package com.visprog.starchive.services

import com.visprog.starchive.models.ArticleModel
import com.visprog.starchive.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ArticleService {
    @POST("/api/articles")
    fun createArticle(@Body article: ArticleModel): Call<GeneralResponseModel>

    @GET("/api/articles")
    fun getAllArticles(): Call<List<ArticleModel>>

    @GET("/api/articles/{articleId}")
    fun getArticleById(@Path("articleId") articleId: Int): Call<ArticleModel>

    @GET("/api/articles/game/{gameId}")
    fun getArticlesByGameId(@Path("gameId") gameId: Int): Call<List<ArticleModel>>

    @PUT("/api/articles/{articleId}")
    fun updateArticle(
        @Path("articleId") articleId: Int,
        @Body article: ArticleModel
    ): Call<GeneralResponseModel>
}