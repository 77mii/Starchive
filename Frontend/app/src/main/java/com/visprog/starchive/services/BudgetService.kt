package com.visprog.starchive.services

import com.visprog.starchive.models.BudgetModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.models.GetBudgetResponse
import retrofit2.Call
import retrofit2.http.*

interface BudgetService {
    @POST("/api/budgets")
    fun createBudget(@Body budget: BudgetModel): Call<GeneralResponseModel>

    @GET("/api/budgets")
    fun getBudgetsByUserId(@Header("X-API-TOKEN") token: String): Call<List<BudgetModel>>

    @GET("/api/budgets/{gameId}")
    fun getBudgetByUserIdAndGameId(
        @Header("X-API-TOKEN") token: String,
        @Path("gameId") gameId: Int
    ): Call<GetBudgetResponse>

    @PUT("/api/budgets/{budgetId}")
    fun updateBudget(
        @Header("X-API-TOKEN") token: String,
        @Path("budgetId") budgetId: Int,
        @Body budget: BudgetModel
    ): Call<GetBudgetResponse>
}