package com.romina.player.infrastructure.driver.request

import kotlinx.serialization.Serializable

@Serializable
data class CreatePlayerRequest(
    val playerName : String
)