package com.romina.combat.application.ports.`in`

import java.util.UUID

data class CreateCombatCommand(
    val playerId : UUID
)
