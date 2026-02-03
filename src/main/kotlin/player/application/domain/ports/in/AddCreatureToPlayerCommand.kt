package com.romina.player.application.domain.ports.`in`

import com.romina.player.application.domain.model.CreatureClass
import java.util.UUID

data class AddCreatureToPlayerCommand(
    val playerId : UUID,
    val creatureName: String,
    val creatureClass: String
)
