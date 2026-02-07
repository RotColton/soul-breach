package com.romina.combat.infrastructure.driver.request

import kotlinx.serialization.Serializable
@Serializable
data class CreateCombatRequest(
    val playerId : String
)
