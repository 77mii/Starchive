package com.visprog.starchive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.visprog.starchive.models.Banner

class BannerViewModel : ViewModel() {
    var banner: Banner? = null
}

class BannerViewModelFactory(private val banner: Banner) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BannerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BannerViewModel().apply { this.banner = banner } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}