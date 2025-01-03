package com.visprog.starchive.models

data class HardPity(
    val pityId: Int,
    val userId: Int,
    val gameId: Int?,
    val bannerId: Int?,
    val pullsTowardsPity: Int
)
