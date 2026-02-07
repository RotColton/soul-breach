package com.romina.combat.application.domain.`in`

import com.romina.combat.application.domain.model.Combat

interface ExecuteCombatActionUseCase {
    fun executeTurn(command : ExecuteTurnCommand): Combat
}