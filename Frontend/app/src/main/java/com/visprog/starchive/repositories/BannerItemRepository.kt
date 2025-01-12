

// package com.visprog.starchive.repositories

// import com.visprog.starchive.models.BannerItemModel
// import com.visprog.starchive.services.BannerItemResponse
// import com.visprog.starchive.services.BannerItemService
// import retrofit2.Call

// interface BannerItemRepository {
//     fun getBannerItemsByBannerId(bannerId: Int): Call<BannerItemResponse>
// }

// class NetworkBannerItemRepository(
//     private val bannerItemService: BannerItemService
// ) : BannerItemRepository {

//     override fun getBannerItemsByBannerId(bannerId: Int): Call<BannerItemResponse> {
//         return bannerItemService.getBannerItemsByBannerId(bannerId)
//     }
// }

package com.visprog.starchive.repositories

import com.visprog.starchive.services.BannerItemResponse
import com.visprog.starchive.services.BannerItemService
import retrofit2.Call

interface BannerItemRepository {
    fun getBannerItemsByBannerId(token: String, bannerId: Int): Call<BannerItemResponse>
}

class NetworkBannerItemRepository(
    private val bannerItemService: BannerItemService
) : BannerItemRepository {

    override fun getBannerItemsByBannerId(token: String, bannerId: Int): Call<BannerItemResponse> {
        return bannerItemService.getBannerItemsByBannerId(token, bannerId)
    }
}