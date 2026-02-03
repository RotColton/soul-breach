package com.romina.player.infrastructure.drive.response

import com.romina.player.infrastructure.drive.dto.CreatureDTO
import com.romina.player.infrastructure.drive.dto.toDTO
import com.romina.player.application.domain.model.Player
import kotlinx.serialization.Serializable

@Serializable
data class PlayerResponse(
    val playerId : String,
    val name: String,
   val creatures : List<CreatureDTO>
)

fun Player.toResponse() = PlayerResponse(
    playerId = playerId.toString(),
    name = name,
    creatures = creatures.map{ it.toDTO() }
)
