// package com.visprog.starchive.services

// import com.visprog.starchive.models.GeneralDataResponse
// import com.visprog.starchive.models.HardPityModel
// import retrofit2.Call
// import retrofit2.http.GET
// import retrofit2.http.Header
// import retrofit2.http.Path
// import retrofit2.http.Query

// interface PityService {
//     @GET("/api/pitybybanneridanduserid/{bannerId}")
//     fun getPityByBannerIdAndUserId(
//         @Header("X-API-TOKEN") token: String,
//         @Path("bannerId") bannerId: Int,
//         @Query("userId") userId: Int
//     ): Call<GeneralDataResponse<HardPityModel>>
// }

package com.visprog.starchive.services

import com.visprog.starchive.models.GeneralDataResponse
import com.visprog.starchive.models.HardPityModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PityService {
    @GET("/api/pitybybanneridandtoken/{bannerId}")
    fun getPityByBannerIdAndToken(
        @Header("X-API-TOKEN") token: String,
        @Path("bannerId") bannerId: Int,
        @Query("token") userToken: String
    ): Call<GeneralDataResponse<HardPityModel>>
    @PUT("/api/pity/{pityId}")
    fun updatePity(
        @Header("X-API-TOKEN") token: String,
        @Path("pityId") pityId: Int,
        @Body pity: HardPityModel
    ): Call<GeneralDataResponse<HardPityModel>>
}