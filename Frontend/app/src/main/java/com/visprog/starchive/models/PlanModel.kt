package com.visprog.starchive.models

import com.google.gson.annotations.SerializedName

data class GetAllPlanResponse(
    val data: List<PlanModel>
)

data class GetPlanResponse(
    val data: PlanModel
)

data class PlanModel(
    @SerializedName("plan_id") val planId: Int,
    @SerializedName("game_id") val gameId: Int,
    @SerializedName("plan_description") val planDescription: String,
    @SerializedName("plan_budget") val planBudget: Float,
    @SerializedName("plan_currency") val planCurrency: Float,
    @SerializedName("plan_tickets") val planTickets: Float
)

data class PlanRequest(
    @SerializedName("game_id") val gameId: Int,
    @SerializedName("plan_description") val planDescription: String,
    @SerializedName("plan_budget") val planBudget: Float,
    @SerializedName("plan_currency") val planCurrency: Float,
    @SerializedName("plan_tickets") val planTickets: Float
)