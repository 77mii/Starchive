
package com.visprog.starchive.repositories

import com.visprog.starchive.models.GameModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.services.GameResponse
import com.visprog.starchive.services.GameService
import retrofit2.Call

interface GameRepository {
    fun createGame(token: String, game: GameModel): Call<GeneralResponseModel>
    fun getAllGames(token: String): Call<GameResponse>
    fun getGameById(token: String, gameId: Int): Call<GameModel>
    fun updateGame(token: String, gameId: Int, game: GameModel): Call<GeneralResponseModel>
}

class NetworkGameRepository(
    private val gameService: GameService
): GameRepository {

    override fun createGame(token: String, game: GameModel): Call<GeneralResponseModel> {
        return gameService.createGame(token, game)
    }

    override fun getAllGames(token: String): Call<GameResponse> {
        return gameService.getAllGames(token)
    }

    override fun getGameById(token: String, gameId: Int): Call<GameModel> {
        return gameService.getGameById(token, gameId)
    }

    override fun updateGame(token: String, gameId: Int, game: GameModel): Call<GeneralResponseModel> {
        return gameService.updateGame(token, gameId, game)
    }
}