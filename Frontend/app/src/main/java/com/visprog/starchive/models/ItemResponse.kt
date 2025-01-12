package com.visprog.starchive.models

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("data") val data: ItemModel
)
