package com.romina.combat.application.ports.`in`

import com.romina.combat.application.domain.model.Combat
import java.util.UUID

interface CombatActionUseCase {
    data class Command(
        val actio : String,
        val activeId : UUID,
        val targetId : UUID,
        val currentCombat : Combat
    )

    suspend fun executeTurn(command : Command): Combat
}