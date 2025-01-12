
package com.visprog.starchive.models

import com.google.gson.annotations.SerializedName

data class GetGameResponse(
    val data: GameModel
)

data class GameModel(
    @SerializedName("game_id") val gameId: Int,
    @SerializedName("game_name") val gameName: String?,
    @SerializedName("income") val income: Float,
    @SerializedName("description") val description: String,
    @SerializedName("currency_name") val currencyName: String?,
    @SerializedName("tickets_name") val ticketsName: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("budgets") val budgets: List<BudgetModel>?,
    @SerializedName("plans") val plans: List<PlanModel>?,
    @SerializedName("expenses") val expenses: List<ExpenseModel>?,
    @SerializedName("banners") val banners: List<BannerModel>?,
    @SerializedName("user_games") val userGames: List<UserGameModel>?,
    @SerializedName("hard_pities") val hardPities: List<HardPityModel>?,
    @SerializedName("articles") val articles: List<ArticleModel>?
)