package com.visprog.starchive.models

data class Game(
    val id: Int,
    val name: String,
    val income: Long,
    val description: String,
    val currency: String,
    val users: List<User>
)
