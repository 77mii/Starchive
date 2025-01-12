package com.visprog.starchive.services

import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.models.GeneralDataResponse
import com.visprog.starchive.models.UserModel
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @POST("api/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>
    
    @GET("/api/users/{token}")
    fun getUserIdByToken(
        @Header("X-API-TOKEN") apiToken: String,
        @Path("token") token: String
    ): Call<GeneralDataResponse<UserModel>>
}