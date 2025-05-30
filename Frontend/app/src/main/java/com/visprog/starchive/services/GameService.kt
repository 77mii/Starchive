
package com.visprog.starchive.services

import com.visprog.starchive.models.GameModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.models.GetGameResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class GameResponse(
    val data: List<GameModel>
)

interface GameService {
    @POST("/api/games")
    fun createGame(
        @Header("X-API-TOKEN") token: String,
        @Body game: GameModel
    ): Call<GeneralResponseModel>

    @GET("/api/games")
    fun getAllGames(
        @Header("X-API-TOKEN") token: String
    ): Call<GameResponse>

    @GET("/api/games/{gameId}")
    fun getGameById(
        @Header("X-API-TOKEN") token: String,
        @Path("gameId") gameId: Int
    ): Call<GetGameResponse>

    @PUT("/api/games/{gameId}")
    fun updateGame(
        @Header("X-API-TOKEN") token: String,
        @Path("gameId") gameId: Int,
        @Body game: GameModel
    ): Call<GeneralResponseModel>
}