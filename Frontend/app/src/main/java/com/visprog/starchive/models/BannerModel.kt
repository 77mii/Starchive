
package com.visprog.starchive.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class BannerModel(
    @SerializedName("banner_id") val bannerId: Int,
    @SerializedName("game_id") val gameId: Int,
    @SerializedName("banner_name") val bannerName: String?,
    @SerializedName("type") val type: String,
    @SerializedName("start_date") val startDate: LocalDateTime?,
    @SerializedName("end_date") val endDate: LocalDateTime?,
    @SerializedName("hard_pity") val hardPity: Int?,
    @SerializedName("soft_pity") val softPity: Int?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("items") val items: List<BannerItemModel>?,
    @SerializedName("hard_pities") val hardPities: List<HardPityModel>?
)