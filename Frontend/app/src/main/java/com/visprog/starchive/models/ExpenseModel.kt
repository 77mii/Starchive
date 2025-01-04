package com.visprog.starchive.models

data class ExpenseModel(
    val expenseId: Int,
    val userId: Int,
    val gameId: Int,
    val spentCurrency: Float,
    val spentMoney: Float,
    val spentTickets: Float
)
