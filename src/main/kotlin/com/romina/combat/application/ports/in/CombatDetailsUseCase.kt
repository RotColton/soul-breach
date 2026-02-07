package com.romina.combat.application.ports.`in`

import com.romina.combat.application.domain.model.Combat

interface CombatDetailsUseCase {
   suspend fun getCombat(command : CombatDetailsCommand) : Combat
}