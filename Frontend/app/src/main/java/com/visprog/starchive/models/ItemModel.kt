package com.visprog.starchive.models

data class ItemModel(
    val itemId: Int,
    val rarity: Int,
    val itemName: String,
    val imageUrl: String?,
    val bannerItems: List<BannerItemModel>
)