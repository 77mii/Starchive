package com.visprog.starchive.models

import java.time.LocalDate

data class BannerModel(
    val id: Int,
    val gameId: Int,
    val name: String,
    val type: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val softPity: Int?,
    val items: List<BannerItemModel>
)





/*
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    val id: Int,
    val gameId: Int,
    val name: String,
    val type: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val softPity: Int?,
    val items: List<BannerItem>
) : Parcelable*/
