package com.visprog.starchive.services

import com.visprog.starchive.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("api/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>
    
}