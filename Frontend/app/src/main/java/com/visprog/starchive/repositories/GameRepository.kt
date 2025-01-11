package com.visprog.starchive.repositories

import com.visprog.starchive.models.GameModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.services.GameService
import retrofit2.Call

interface GameRepository {
    fun createGame(game: GameModel): Call<GeneralResponseModel>
    fun getAllGames(): Call<List<GameModel>>
    fun getGameById(gameId: Int): Call<GameModel>
    fun updateGame(gameId: Int, game: GameModel): Call<GeneralResponseModel>
}

class NetworkGameRepository(
    private val gameService: GameService
): GameRepository {

    override fun createGame(game: GameModel): Call<GeneralResponseModel> {
        return gameService.createGame(game)
    }

    override fun getAllGames(): Call<List<GameModel>> {
        return gameService.getAllGames()
    }

    override fun getGameById(gameId: Int): Call<GameModel> {
        return gameService.getGameById(gameId)
    }

    override fun updateGame(gameId: Int, game: GameModel): Call<GeneralResponseModel> {
        return gameService.updateGame(gameId, game)
    }
}