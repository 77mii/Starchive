package com.visprog.starchive.repositories

import com.visprog.starchive.models.BannerModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.services.BannerService
import retrofit2.Call

interface BannerRepository {
    fun createBanner(banner: BannerModel): Call<GeneralResponseModel>
    fun getBannersByGameId(gameId: Int): Call<List<BannerModel>>
    fun updateBanner(bannerId: Int, banner: BannerModel): Call<GeneralResponseModel>
    fun getActiveBanners(): Call<List<BannerModel>>
}

class NetworkBannerRepository(
    private val bannerService: BannerService
): BannerRepository {

    override fun createBanner(banner: BannerModel): Call<GeneralResponseModel> {
        return bannerService.createBanner(banner)
    }

    override fun getBannersByGameId(gameId: Int): Call<List<BannerModel>> {
        return bannerService.getBannersByGameId(gameId)
    }

    override fun updateBanner(bannerId: Int, banner: BannerModel): Call<GeneralResponseModel> {
        return bannerService.updateBanner(bannerId, banner)
    }

    override fun getActiveBanners(): Call<List<BannerModel>> {
        return bannerService.getActiveBanners()
    }
}