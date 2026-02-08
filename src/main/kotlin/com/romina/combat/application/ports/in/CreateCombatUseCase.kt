package com.romina.combat.application.ports.`in`

import com.romina.combat.application.domain.model.Combat
import java.util.UUID

interface CreateCombatUseCase {
    data class Command(
        val playerId : UUID
    )

    suspend fun createCombatWithDefaultEnemy(createCombatCommand : Command) : Combat
}