package com.visprog.starchive.repositories

import com.visprog.starchive.models.UserResponse
import com.visprog.starchive.services.AuthAPIService
import retrofit2.Call

interface AuthRepository {
    fun register(username: String, password: String): Call<UserResponse>

    fun login(username: String, password: String): Call<UserResponse>
}

class NetworkAuthRepository(
    private val authAPIService: AuthAPIService
): AuthRepository {
    override fun register(username: String, password: String): Call<UserResponse> {
        var registerMap = HashMap<String, String>()

        registerMap["username"] = username
        registerMap["password"] = password

        return authAPIService.register(registerMap)
    }

    override fun login(username: String, password: String): Call<UserResponse> {
        var loginMap = HashMap<String, String>()

        loginMap["username"] = username
        loginMap["password"] = password

        return authAPIService.login(loginMap)
    }
}