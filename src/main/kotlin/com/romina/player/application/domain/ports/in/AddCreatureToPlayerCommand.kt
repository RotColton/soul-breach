package com.romina.player.application.domain.ports.`in`

import java.util.UUID
//TODO: validate creature class enum
data class AddCreatureToPlayerCommand(
    val playerId : UUID,
    val creatureName: String,
    val creatureClass: String
)
