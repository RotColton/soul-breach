package com.romina.player.infrastructure.driver.request

import kotlinx.serialization.Serializable
//TODO: validation rules
@Serializable
data class CreatePlayerRequest(
    val playerName : String
)