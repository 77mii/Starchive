

package com.visprog.starchive.repositories

import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.services.BannerResponse
import com.visprog.starchive.services.BannerService
import retrofit2.Call

interface BannerRepository {
    fun createBanner(token: String, banner: BannerModel): Call<GeneralResponseModel>
    fun getBannersByGameId(token: String, gameId: Int): Call<BannerResponse>
    fun updateBanner(token: String, bannerId: Int, banner: BannerModel): Call<GeneralResponseModel>
    fun getActiveBanners(token: String): Call<BannerResponse>
}

class NetworkBannerRepository(
    private val bannerService: BannerService
): BannerRepository {

    override fun createBanner(token: String, banner: BannerModel): Call<GeneralResponseModel> {
        return bannerService.createBanner(token, banner)
    }

    override fun getBannersByGameId(token: String, gameId: Int): Call<BannerResponse> {
        return bannerService.getBannersByGameId(token, gameId)
    }

    override fun updateBanner(token: String, bannerId: Int, banner: BannerModel): Call<GeneralResponseModel> {
        return bannerService.updateBanner(token, bannerId, banner)
    }

    override fun getActiveBanners(token: String): Call<BannerResponse> {
        return bannerService.getActiveBanners(token)
    }
}

