package com.visprog.starchive.models

data class UserResponse(
    val data: UserModel
)

data class UserModel(
    val userId: Int,
    val username: String,
    val password: String,
    val budgets: List<BudgetModel>,
    val plans: List<PlanModel>,
    val expenses: List<ExpenseModel>,
    val userGames: List<UserGameModel>,
    val hardPities: List<HardPityModel>,
    val token: String?
)