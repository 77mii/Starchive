package com.visprog.starchive.services

import com.visprog.starchive.models.ArticleModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.models.GetAllArticleResponse
import com.visprog.starchive.models.GetArticleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ArticleService {
    @POST("/api/articles")
    fun createArticle(@Body article: ArticleModel): Call<GeneralResponseModel>

    @GET("/api/articles")
    fun getAllArticles(): Call<List<GetArticleResponse>>

    @GET("/api/articles/{articleId}")
    fun getArticleById(
        @Header("X-API-TOKEN") token: String,
        @Path("articleId") articleId: Int
    ): Call<GetArticleResponse>

    @GET("/api/articles/game/{gameId}")
    fun getArticlesByGameId(
        @Header("X-API-TOKEN") token: String,
        @Path("gameId") gameId: Int
    ): Call<GetAllArticleResponse>

    @PUT("/api/articles/{articleId}")
    fun updateArticle(
        @Header("X-API-TOKEN") token: String,
        @Path("articleId") articleId: Int,
        @Body article: ArticleModel
    ): Call<GeneralResponseModel>
}