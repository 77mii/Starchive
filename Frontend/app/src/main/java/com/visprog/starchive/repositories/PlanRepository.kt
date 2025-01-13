package com.visprog.starchive.repositories

import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.models.GetPlanResponse
import com.visprog.starchive.models.PlanModel
import com.visprog.starchive.models.GetAllPlanResponse
import com.visprog.starchive.models.PlanRequest
import com.visprog.starchive.services.PlanService
import retrofit2.Call

interface PlanRepository {
    fun createPlan(token: String, plan: PlanRequest): Call<GeneralResponseModel>
    fun getPlansByUserId(token: String): Call<GetAllPlanResponse>
    fun getPlanById(token: String, planId: Int): Call<GetPlanResponse>
    fun deletePlan(token: String, planId: Int): Call<GeneralResponseModel>
}

class NetworkPlanRepository(
    private val planService: PlanService
) : PlanRepository {

    override fun createPlan(token: String, plan: PlanRequest): Call<GeneralResponseModel> {
        return planService.createPlan(token, plan)
    }

    override fun getPlansByUserId(token: String): Call<GetAllPlanResponse> {
        return planService.getPlansByUserId(token)
    }

    override fun getPlanById(token: String, planId: Int): Call<GetPlanResponse> {
        return planService.getPlanById(token, planId)
    }

    override fun deletePlan(token: String, planId: Int): Call<GeneralResponseModel> {
        return planService.deletePlan(token, planId)
    }
}