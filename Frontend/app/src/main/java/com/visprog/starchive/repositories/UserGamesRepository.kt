package com.visprog.starchive.repositories



import com.visprog.starchive.models.UserGameModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.services.UserGameService
import retrofit2.Call

interface UserGamesRepository {
    fun addUserGame(userGame: UserGameModel): Call<GeneralResponseModel>
    fun getUserGamesByUserId(userId: Int): Call<List<UserGameModel>>
    fun removeUserGame(userId: Int, gameId: Int): Call<GeneralResponseModel>
}

class NetworkUserGamesRepository(
    private val userGameService: UserGameService
): UserGamesRepository {

    override fun addUserGame(userGame: UserGameModel): Call<GeneralResponseModel> {
        return userGameService.addUserGame(userGame)
    }

    override fun getUserGamesByUserId(userId: Int): Call<List<UserGameModel>> {
        return userGameService.getUserGamesByUserId(userId)
    }

    override fun removeUserGame(userId: Int, gameId: Int): Call<GeneralResponseModel> {
        return userGameService.removeUserGame(userId, gameId)
    }
}