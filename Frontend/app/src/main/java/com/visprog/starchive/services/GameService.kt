package com.visprog.starchive.services

import com.visprog.starchive.models.GameModel
import com.visprog.starchive.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GameService {
    @POST("/api/games")
    fun createGame(@Body game: GameModel): Call<GeneralResponseModel>

    @GET("/api/games")
    fun getAllGames(): Call<List<GameModel>>

    @GET("/api/games/{gameId}")
    fun getGameById(@Path("gameId") gameId: Int): Call<GameModel>

    @PUT("/api/games/{gameId}")
    fun updateGame(
        @Path("gameId") gameId: Int,
        @Body game: GameModel
    ): Call<GeneralResponseModel>
}