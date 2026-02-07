package com.romina.player.infrastructure.driver.response.dto

import com.romina.player.application.domain.model.Creature
import kotlinx.serialization.Serializable

@Serializable
data class CreatureDTO(
    val id : String,
    val name : String,
    val creatureClass : String,
    val level: Int,
    val xp: Int,
    val attributes : AttributeDTO
)

fun Creature.toDTO() = CreatureDTO(
    id = id.toString(),
    name = name,
    creatureClass = creatureClass.name,
    level = level,
    xp = xp,
    attributes = attributes.toDTO()
)