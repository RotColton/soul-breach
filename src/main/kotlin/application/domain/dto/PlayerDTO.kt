package com.romina.application.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO(
    private val playerId : String,
    private val name : String,
    private val creatures: MutableList<CreatureDTO>)
