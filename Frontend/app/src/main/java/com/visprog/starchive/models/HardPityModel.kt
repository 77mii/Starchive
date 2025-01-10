/*
package com.visprog.starchive.models

data class HardPityModel(
    val pityId: Int,
    val userId: Int,
    val gameId: Int?,
    val bannerId: Int?,
    val pityThreshold: Int?,
    val pullsTowardsPity: Int
)
*/


package com.visprog.starchive.models

data class HardPityModel(
    val pityId: Int,
    val userId: Int,
    val gameId: Int,
    val bannerId: Int,
    val pullTowardsPity: Int
)