package com.visprog.starchive.models

data class PlanModel(
    val planId: Int,
    val userId: Int,
    val gameId: Int,
    val planDescription: String,
    val planBudget: Float,
    val planCurrency: Float,
    val planTickets: Float
)