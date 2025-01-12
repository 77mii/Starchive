package com.visprog.starchive.services

import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.GeneralDataResponse
import com.visprog.starchive.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class BannerResponse(val data: List<BannerModel>)

interface BannerService {
    @POST("/api/banners")
    fun createBanner(
        @Header("X-API-TOKEN") token: String,
        @Body banner: BannerModel
    ): Call<GeneralResponseModel>

    @GET("/api/banners/{gameId}")
    fun getBannersByGameId(
        @Header("X-API-TOKEN") token: String,
        @Path("gameId") gameId: Int
    ): Call<BannerResponse>

    @PUT("/api/banners/{bannerId}")
    fun updateBanner(
        @Header("X-API-TOKEN") token: String,
        @Path("bannerId") bannerId: Int,
        @Body banner: BannerModel
    ): Call<GeneralResponseModel>

    @GET("/api/banners/active")
    fun getActiveBanners(
        @Header("X-API-TOKEN") token: String
    ): Call<BannerResponse>

    @GET("/api/bannersbyid/{bannerId}")
    fun getBannerById(
        @Header("X-API-TOKEN") token: String,
        @Path("bannerId") bannerId: Int
    ): Call<GeneralDataResponse<BannerModel>>
}