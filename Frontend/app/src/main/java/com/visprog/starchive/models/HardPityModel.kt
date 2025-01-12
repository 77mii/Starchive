


package com.visprog.starchive.models
import com.google.gson.annotations.SerializedName
data class HardPityModel(
    @SerializedName("pity_id") val pityId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("game_id") val gameId: Int,
    @SerializedName("banner_id") val bannerId: Int,
    @SerializedName("pull_towards_pity") val pullTowardsPity: Int
)