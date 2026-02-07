package com.romina.combat.application.ports.`in`

import com.romina.combat.application.domain.model.Combat

interface ExecuteCombatActionUseCase {
    suspend fun executeTurn(command : ExecuteTurnCommand): Combat
}