package com.visprog.starchive.repositories

import com.visprog.starchive.models.BudgetModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.models.GetBudgetResponse
import com.visprog.starchive.services.BudgetService
import retrofit2.Call

interface BudgetRepository {
    fun createBudget(budget: BudgetModel): Call<GeneralResponseModel>
    fun getBudgetsByUserId(token: String): Call<List<BudgetModel>>
    fun getBudgetByUserIdAndGameId(token: String, gameId: Int): Call<GetBudgetResponse>
    fun updateBudget(token: String, budgetId: Int, budget: BudgetModel): Call<GetBudgetResponse>
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

    override fun getBudgetByUserIdAndGameId(token: String, gameId: Int): Call<GetBudgetResponse> {
        return budgetService.getBudgetByUserIdAndGameId(token, gameId)
    }

    override fun updateBudget(token: String, budgetId: Int, budget: BudgetModel): Call<GetBudgetResponse> {
        return budgetService.updateBudget(token, budgetId, budget)
    }
}