package com.romina.player.infrastructure.driver.response.dto

import com.romina.player.application.domain.model.ElementalsAttributes
import kotlinx.serialization.Serializable

@Serializable
data class AttributeDTO(
    val hp: Int,
    val attack : Int,
    val speed : Int
)
fun ElementalsAttributes.toDTO() = AttributeDTO(
    hp = hp,
    attack = attack,
    speed = speed
)