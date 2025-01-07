package com.visprog.starchive.models

data class GetAllBudgetResponse(
    val data: List<BudgetModel>
)

data class GetBudgetResponse(
    val data: BudgetModel
)

data class BudgetModel(
    val budgetId: Int,
    val userId: Int,
    val gameId: Int,
    val allocatedBudget: Float,
    val allocatedCurrency: Float,
    val allocatedTickets: Float,
    val remainingBudget: Float,
    val remainingCurrency: Float,
    val remainingTickets: Float
)

data class BudgetRequest(
    val userId: Int,
    val gameId: Int,
    val allocatedBudget: Float,
    val allocatedCurrency: Float,
    val allocatedTickets: Float,
    val remainingBudget: Float,
    val remainingCurrency: Float,
    val remainingTickets: Float
)