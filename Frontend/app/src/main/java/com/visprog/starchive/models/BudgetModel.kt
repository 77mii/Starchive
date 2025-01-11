package com.visprog.starchive.models

data class GetAllBudgetResponse(
    val data: List<BudgetModel>
)

data class GetBudgetResponse(
    val data: BudgetModel
)

data class BudgetModel(
    val budgetId: Int,
    val gameId: Int,
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