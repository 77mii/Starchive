package com.visprog.starchive.viewmodels



import androidx.lifecycle.ViewModel
import com.visprog.starchive.models.BannerItemModel

class BannerItemViewModel : ViewModel() {
    var bannerItemModels: List<BannerItemModel> = emptyList()
        private set

    fun setBannerItems(items: List<BannerItemModel>) {
        bannerItemModels = items
    }
}