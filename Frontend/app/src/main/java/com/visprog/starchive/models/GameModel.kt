package com.visprog.starchive.models

data class GameModel(
    val id: Int,
    val name: String,
    val income: Long,
    val description: String,
    val currency: String,
    val userModels: List<UserModel>
)
