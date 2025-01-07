package com.visprog.starchive.repositories

import com.visprog.starchive.models.BudgetModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.services.BudgetService
import retrofit2.Call

interface BudgetRepository {
    fun createBudget(budget: BudgetModel): Call<GeneralResponseModel>
    fun getBudgetsByUserId(token: String): Call<List<BudgetModel>>
    fun getBudgetByUserIdAndGameId(token: String, gameId: Int): Call<BudgetModel>
    fun updateBudget(budgetId: Int, budget: BudgetModel): Call<GeneralResponseModel>
}

class NetworkBudgetRepository(
    private val budgetService: BudgetService
): BudgetRepository {

    override fun createBudget(budget: BudgetModel): Call<GeneralResponseModel> {
        return budgetService.createBudget(budget)
    }

    override fun getBudgetsByUserId(token: String): Call<List<BudgetModel>> {
        return budgetService.getBudgetsByUserId(token)
    }

    override fun getBudgetByUserIdAndGameId(token: String, gameId: Int): Call<BudgetModel> {
        return budgetService.getBudgetByUserIdAndGameId(token, gameId)
    }

    override fun updateBudget(budgetId: Int, budget: BudgetModel): Call<GeneralResponseModel> {
        return budgetService.updateBudget(budgetId, budget)
    }
}