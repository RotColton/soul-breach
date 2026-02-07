package com.romina.player.infrastructure.driver.response

import kotlinx.serialization.Serializable

@Serializable
data class CreatePlayerResponse(
    val id: String
) {
}