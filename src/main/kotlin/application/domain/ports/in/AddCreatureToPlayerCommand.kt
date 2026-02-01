package com.romina.application.domain.ports.`in`

import com.romina.application.domain.model.CreatureClass
import java.util.UUID

data class AddCreatureToPlayerCommand(
    val playerId : UUID,
    val creatureName: String,
    val creatureClass: CreatureClass
)
