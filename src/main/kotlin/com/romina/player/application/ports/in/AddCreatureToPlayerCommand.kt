package com.romina.player.application.ports.`in`

import java.util.UUID
//TODO: validate creature class enum
data class AddCreatureToPlayerCommand(
    val playerId : UUID,
    val creatureName: String,
    val creatureClass: String
)
