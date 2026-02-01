package com.romina.infrastructure.driver.rest.routes.player.reponse

import com.romina.application.domain.dto.CreatureDTO
import com.romina.application.domain.dto.toDTO
import com.romina.application.domain.model.Player
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
