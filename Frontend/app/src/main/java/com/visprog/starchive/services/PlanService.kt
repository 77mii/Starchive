package com.visprog.starchive.services

import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.models.GetPlanResponse
import com.visprog.starchive.models.PlanModel
import com.visprog.starchive.models.GetAllPlanResponse
import com.visprog.starchive.models.PlanRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PlanService {
    @POST("/api/plans")
    fun createPlan(
        @Header("X-API-TOKEN") token: String,
        @Body plan: PlanRequest
    ): Call<GeneralResponseModel>

    @GET("/api/plans")
    fun getPlansByUserId(
        @Header("X-API-TOKEN") token: String
    ): Call<GetAllPlanResponse>

    @GET("/api/plans/{planId}")
    fun getPlanById(
        @Header("X-API-TOKEN") token: String,
        @Path("planId") planId: Int
    ): Call<GetPlanResponse>

    @DELETE("/api/plans/{planId}")
    fun deletePlan(
        @Header("X-API-TOKEN") token: String,
        @Path("planId") planId: Int
    ): Call<GeneralResponseModel>
}