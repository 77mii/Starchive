package com.visprog.starchive.services

import com.visprog.starchive.models.UserGameModel
import com.visprog.starchive.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/*
interface UserGameService {
    @POST("/api/user-games")
    fun addUserGame(@Body userGame: UserGameModel): Call<GeneralResponseModel>

    @GET("/api/user-games")
    fun getUserGamesByUserId(): Call<List<UserGameModel>>

    @DELETE("/api/user-games/{gameId}")
    fun removeUserGame(@Path("gameId") gameId: Int): Call<GeneralResponseModel>
}*/

interface UserGameService {
    @POST("/api/user-games")
    fun addUserGame(@Body userGame: UserGameModel): Call<GeneralResponseModel>

    @GET("/api/user-games")
    fun getUserGamesByUserId(@Query("userId") userId: Int): Call<List<UserGameModel>>

    @DELETE("/api/user-games/{gameId}")
    fun removeUserGame(
        @Query("userId") userId: Int,
        @Path("gameId") gameId: Int
    ): Call<GeneralResponseModel>
}