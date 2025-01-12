

// package com.visprog.starchive.services

// import com.visprog.starchive.models.BannerItemModel
// import retrofit2.Call
// import retrofit2.http.GET
// import retrofit2.http.Path

// data class BannerItemResponse(val data: List<BannerItemModel>)

// interface BannerItemService {
//     @GET("/api/banneritems/{bannerId}")
//     fun getBannerItemsByBannerId(@Path("bannerId") bannerId: Int): Call<BannerItemResponse>
// }

package com.visprog.starchive.services

import com.visprog.starchive.models.BannerItemModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

data class BannerItemResponse(val data: List<BannerItemModel>)

interface BannerItemService {
    @GET("/api/banner-items/{bannerId}")
    fun getBannerItemsByBannerId(
        @Header("X-API-TOKEN") token: String,
        @Path("bannerId") bannerId: Int
    ): Call<BannerItemResponse>
}