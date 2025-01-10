package com.visprog.starchive.services

import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BannerService {
    @POST("/api/banners")
    fun createBanner(@Body banner: BannerModel): Call<GeneralResponseModel>

    @GET("/api/banners/{gameId}")
    fun getBannersByGameId(@Path("gameId") gameId: Int): Call<List<BannerModel>>

    @PUT("/api/banners/{bannerId}")
    fun updateBanner(
        @Path("bannerId") bannerId: Int,
        @Body banner: BannerModel
    ): Call<GeneralResponseModel>

    @GET("/api/banners/active")
    fun getActiveBanners(): Call<List<BannerModel>>
}