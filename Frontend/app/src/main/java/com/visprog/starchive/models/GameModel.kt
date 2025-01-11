

package com.visprog.starchive.models

data class GameModel(
    val gameId: Int,
    val gameName: String,
    val income: Float,
    val description: String,
    val currencyName: String,
    val ticketsName: String?,
    val imageUrl: String?,
    val budgets: List<BudgetModel>,
    val plans: List<PlanModel>,
    val expenses: List<ExpenseModel>,
    val banners: List<BannerModel>,
    val userGames: List<UserGameModel>,
    val hardPities: List<HardPityModel>,
    val articles: List<ArticleModel>
)