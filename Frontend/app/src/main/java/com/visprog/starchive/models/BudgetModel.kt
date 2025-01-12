package com.visprog.starchive.models

import com.google.gson.annotations.SerializedName

data class GetAllBudgetResponse(
    val data: List<BudgetModel>
)

data class GetBudgetResponse(
    val data: BudgetModel
)

data class BudgetModel(
    @SerializedName("budget_id") val budgetId: Int,
    @SerializedName("game_id") val gameId: Int,
    val allocated_budget: Float,
    val allocated_currency: Float,
    val allocated_tickets: Float,
    val remaining_budget: Float,
    val remaining_currency: Float,
    val remaining_tickets: Float
)

data class BudgetRequest(
    val gameId: Int,
    val allocated_budget: Float,
    val allocated_currency: Float,
    val allocated_tickets: Float,
    val remaining_budget: Float,
    val remaining_currency: Float,
    val remaining_tickets: Float
)