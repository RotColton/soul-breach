package com.romina.combat.application.domain.`in`

import com.romina.combat.application.domain.model.Combat

interface ExecuteCombatActionUseCase {
    suspend fun executeTurn(command : ExecuteTurnCommand): Combat
}