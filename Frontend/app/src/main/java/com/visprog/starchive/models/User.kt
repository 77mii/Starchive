package com.visprog.starchive.models

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val money: Double,
    val games: List<Game>
)