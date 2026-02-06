package com.romina.combat.infrastructure.drive.request

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
@Serializable
data class CreateCombatRequest(
    @Contextual
    val playerId : String
)
