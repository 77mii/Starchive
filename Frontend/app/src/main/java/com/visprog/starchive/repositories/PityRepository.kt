// package com.visprog.starchive.repositories

// import com.visprog.starchive.models.GeneralDataResponse
// import com.visprog.starchive.models.HardPityModel
// import com.visprog.starchive.services.PityService
// import retrofit2.Call

// interface PityRepository {
//     fun getPityByBannerIdAndUserId(token: String, bannerId: Int, userId: Int): Call<GeneralDataResponse<HardPityModel>>
// }

// class NetworkPityRepository(
//     private val pityService: PityService
// ) : PityRepository {
//     override fun getPityByBannerIdAndUserId(token: String, bannerId: Int, userId: Int): Call<GeneralDataResponse<HardPityModel>> {
//         return pityService.getPityByBannerIdAndUserId(token, bannerId, userId)
//     }
// }

package com.visprog.starchive.repositories

import com.visprog.starchive.models.GeneralDataResponse
import com.visprog.starchive.models.HardPityModel
import com.visprog.starchive.services.PityService
import retrofit2.Call

interface PityRepository {
    fun getPityByBannerIdAndToken(token: String, bannerId: Int, userToken: String): Call<GeneralDataResponse<HardPityModel>>
    fun updatePity(token: String, pityId: Int, pity: HardPityModel): Call<GeneralDataResponse<HardPityModel>>
}

class NetworkPityRepository(
    private val pityService: PityService
) : PityRepository {
    override fun getPityByBannerIdAndToken(token: String, bannerId: Int, userToken: String): Call<GeneralDataResponse<HardPityModel>> {
        return pityService.getPityByBannerIdAndToken(token, bannerId, userToken)
    }
    override fun updatePity(token: String, pityId: Int, pity: HardPityModel): Call<GeneralDataResponse<HardPityModel>> {
        return pityService.updatePity(token, pityId, pity)
    }
}
