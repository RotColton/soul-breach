package com.romina.player.infrastructure.adapters.driver.rest.dto

import com.romina.player.application.domain.model.Player
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO(
    val playerId : String,
    val name : String,
    val creatures: List<CreatureDTO>
)

fun Player.toDTO() = PlayerDTO(
    playerId = playerId.toString(),
    name = name,
    creatures = creatures.map{ it.toDTO() }
)