package com.visprog.starchive.repositories

import com.visprog.starchive.models.GeneralDataResponse
import com.visprog.starchive.models.HardPityModel
import com.visprog.starchive.services.PityService
import retrofit2.Call

interface PityRepository {
    fun getPityByBannerIdAndUserId(token: String, bannerId: Int, userId: Int): Call<GeneralDataResponse<HardPityModel>>
}

class NetworkPityRepository(
    private val pityService: PityService
) : PityRepository {
    override fun getPityByBannerIdAndUserId(token: String, bannerId: Int, userId: Int): Call<GeneralDataResponse<HardPityModel>> {
        return pityService.getPityByBannerIdAndUserId(token, bannerId, userId)
    }
}