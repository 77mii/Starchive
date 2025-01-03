package com.visprog.starchive.viewmodels



import androidx.lifecycle.ViewModel
import com.visprog.starchive.models.BannerItem

class BannerItemViewModel : ViewModel() {
    var bannerItems: List<BannerItem> = emptyList()
        private set

    fun setBannerItems(items: List<BannerItem>) {
        bannerItems = items
    }
}