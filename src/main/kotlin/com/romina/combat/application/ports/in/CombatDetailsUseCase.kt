package com.romina.combat.application.ports.`in`

import com.romina.combat.application.domain.model.Combat
import java.util.UUID

interface CombatDetailsUseCase {
    data class Command(
        val combatId: UUID
    )

    suspend fun combatDetails(command: Command): Combat
}