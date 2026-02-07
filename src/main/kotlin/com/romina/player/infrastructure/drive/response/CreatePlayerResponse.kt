package com.romina.player.infrastructure.drive.response

import kotlinx.serialization.Serializable

@Serializable
data class CreatePlayerResponse(
    val id: String
) {
}