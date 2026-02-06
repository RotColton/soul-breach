package com.romina.combat.infrastructure.drive.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCombatRequest(
    val playerId : String
)
