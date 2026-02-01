package com.romina.application.domain.dto

import com.romina.application.domain.model.Attribute
import kotlinx.serialization.Serializable

@Serializable
data class AttributeDTO(
    val hp: Int,
    val attack : Int,
    val speed : Int
)
fun Attribute.toDTO() = AttributeDTO(
    hp = hp,
    attack = attack,
    speed = speed
)