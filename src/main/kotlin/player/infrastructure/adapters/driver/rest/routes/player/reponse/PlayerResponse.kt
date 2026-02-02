package com.romina.player.infrastructure.adapters.driver.rest.routes.player.reponse

import com.romina.player.infrastructure.adapters.driver.rest.dto.CreatureDTO
import com.romina.player.infrastructure.adapters.driver.rest.dto.toDTO
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
