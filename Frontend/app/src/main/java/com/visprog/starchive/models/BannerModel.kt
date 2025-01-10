/*
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





*/

package com.visprog.starchive.models

import java.time.LocalDateTime

data class BannerModel(
    val bannerId: Int,
    val gameId: Int,
    val bannerName: String,
    val type: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val hardPity: Int?,
    val softPity: Int?,
    val imageUrl: String?,
    val items: List<BannerItemModel>,
    val hardPities: List<HardPityModel>
)