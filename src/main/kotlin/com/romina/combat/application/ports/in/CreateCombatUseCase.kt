package com.romina.combat.application.ports.`in`

import com.romina.combat.application.domain.model.Combat

interface CreateCombatUseCase {
    suspend fun createCombatWithDefaultEnemy(createCombatCommand : CreateCombatCommand) : Combat
}