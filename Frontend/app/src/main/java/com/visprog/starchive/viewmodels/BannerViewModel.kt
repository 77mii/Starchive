package com.visprog.starchive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.visprog.starchive.models.BannerModel

class BannerViewModel : ViewModel() {
    var bannerModel: BannerModel? = null
}

class BannerViewModelFactory(private val bannerModel: BannerModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BannerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BannerViewModel().apply { this.bannerModel = bannerModel } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}