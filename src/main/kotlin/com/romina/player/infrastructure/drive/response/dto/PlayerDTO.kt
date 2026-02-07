package com.romina.player.infrastructure.drive.response.dto

import com.romina.player.application.domain.model.Player
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO(
    val playerId : String,
    val name : String,
    val creatures: List<CreatureDTO>
)

fun Player.toDTO() = PlayerDTO(
    playerId = id.toString(),
    name = name,
    creatures = creatures.map{ it.toDTO() }
)