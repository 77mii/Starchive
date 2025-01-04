package com.visprog.starchive.models

data class BannerItemModel(
    val bannerId: Int,
    val id: Int,
    val rarity: String,
    val itemName: String,
    val acquireRate: Float
)
