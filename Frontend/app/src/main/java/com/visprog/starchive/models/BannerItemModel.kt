

package com.visprog.starchive.models

import com.google.gson.annotations.SerializedName

data class BannerItemModel(
    @SerializedName("banner_id") val bannerId: Int,
    @SerializedName("item_id") val itemId: Int,
    @SerializedName("acquire_rate") val acquireRate: Float
)