package com.romina.player.application.domain.ports.`in`

import kotlinx.serialization.Serializable

data class CreatePlayerCommand(
    val playerName: String
)