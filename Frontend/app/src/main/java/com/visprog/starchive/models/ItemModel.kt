package com.visprog.starchive.models

import com.google.gson.annotations.SerializedName

data class ItemModel(
    @SerializedName("item_id") val itemId: Int,
    @SerializedName("rarity") val rarity: Int,
    @SerializedName("item_name") val itemName: String,
    @SerializedName("image_url") val imageUrl: String?
)